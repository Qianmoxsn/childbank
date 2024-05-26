package com.seg83.childbank.TestService;

import com.seg83.childbank.dao.DepositAccountBillsDao;
import com.seg83.childbank.service.CurrentService;
import com.seg83.childbank.service.DepositService;
import com.seg83.childbank.service.HistoryService;
import com.seg83.childbank.utils.StringDateConvert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * TestDepositService is a test class for testing the DepositService class.
 */
@SpringBootTest
@Slf4j
class TestDepositService {

    /**
     * DepositService instance for handling deposit operations.
     */
    @Autowired
    private DepositService depositService;

    /**
     * DepositAccountBillsDao instance for handling deposit account bills data access.
     */
    @Autowired
    private DepositAccountBillsDao depositAccountBillsDao;

    /**
     * CurrentService instance for handling current account operations.
     */
    @Autowired
    private CurrentService currentService;

    /**
     * HistoryService instance for handling history operations.
     */
    @Autowired
    private HistoryService historyService;

    /**
     * StringDateConvert instance for converting date strings to Date objects.
     */
    @Autowired
    private StringDateConvert stringDateConvert;

    /**
     * Sets up the test environment before all tests.
     */
    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        // 删除所有现有的存款账单
        depositAccountBillsDao.deleteAllDepositAccountBills();
        // 创建初始测试数据
        depositService.createDepositAccountBill(100.0, 0.5, "2019-01-01", "2025-01-01");
    }

    /**
     * Restores the test JSON file after each test.
     */
    @AfterEach
    void restoreTestJson() {
        try {
            Files.deleteIfExists(Path.of("data.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Remove :: test data json\n");
    }

    /**
     * Tests the generateDepositList() method.
     */
    @Test
    void generateDepositList() {
        depositService.createDepositAccountBill(200, 0.1, "2023-08-03", "2024-05-01");
        depositService.createDepositAccountBill(200, 0.1, "2023-08-03", "2024-05-01");
        depositService.createDepositAccountBill(200, 0.1, "2023-08-03", "2024-05-01");
        Object[][] depositList = depositService.generateDepositList();
        assertNotNull(depositList, "Deposit list should not be null");
        assertTrue(depositList.length > 0, "Deposit list should not be empty");
        System.out.println(Arrays.deepToString(depositList));
    }

    /**
     * Tests the createDepositAccountBill() method.
     */

    @Test
    void createDepositAccountBill() {
        long initialElementCount = depositAccountBillsDao.ElementCount;

        depositService.createDepositAccountBill(100, 0.1, "2023-08-03", "2024-08-03");

        long newElementCount = depositAccountBillsDao.ElementCount;
        assertEquals(initialElementCount + 1, newElementCount, "Element count should have incremented by 1");

        Object amount = depositAccountBillsDao.getAttribute("depositAccountBillAmount", newElementCount);
        assertNotNull(amount, "Deposit account bill amount should not be null");
        assertEquals(100.0, amount, "Deposit account bill amount should be 100.0");

        Object rate = depositAccountBillsDao.getAttribute("depositAccountBillRate", newElementCount);
        assertNotNull(rate, "Deposit account bill rate should not be null");
        assertEquals(0.1, rate, "Deposit account bill rate should be 0.1");

        Object effectiveDate = depositAccountBillsDao.getAttribute("depositAccountBillEffectiveDate", newElementCount);
        assertNotNull(effectiveDate, "Deposit account bill effective date should not be null");
        assertEquals("2023-08-03", effectiveDate, "Deposit account bill effective date should be '2023-08-03'");

        Object expireDate = depositAccountBillsDao.getAttribute("depositAccountBillExpireDate", newElementCount);
        assertNotNull(expireDate, "Deposit account bill expire date should not be null");
        assertEquals("2024-08-03", expireDate, "Deposit account bill expire date should be '2024-08-03'");
    }
        /**
         * Tests the depositFixAccount() method.
         */
        @Test
        void depositFixAccount() {
            double initialBalance = currentService.checkCurrentAccountBalance();

            depositService.depositFixAccount(100, 0.1, "2023-08-03", "2024-08-03");

            double newBalance = currentService.checkCurrentAccountBalance();
            assertEquals(initialBalance - 100, newBalance, "Current account balance should be decreased by 100");

            long newElementCount = depositAccountBillsDao.ElementCount;
            assertEquals(2, newElementCount);

            Object amount = depositAccountBillsDao.getAttribute("depositAccountBillAmount", newElementCount);
            assertNotNull(amount, "Deposit account bill amount should not be null");
            assertEquals(100.0, amount, "Deposit account bill amount should be 100.0");
        }

        /**
         * Tests the processMaturedDeposits() method.
         */
        @Test
        void processMaturedDeposits() {
            double initialBalance = currentService.checkCurrentAccountBalance();

            // 创建一个已到期的定期存款账单
            depositService.createDepositAccountBill(200, 0.1, "2023-08-03", "2024-05-01");
            assertEquals(initialBalance, currentService.checkCurrentAccountBalance());

            depositService.processMaturedDeposits();

            double newBalance = currentService.checkCurrentAccountBalance();
            double expectedInterest = 200 * 0.1 * 272 / 365; // Assuming the deposit has been active for 273 days
            double expectedBalance = initialBalance + 200 + expectedInterest;
            assertEquals(expectedBalance, newBalance, 0.01, "Current account balance should include principal and interest");

            List<Object> bills = depositAccountBillsDao.getAllAttributes();
            assertFalse(bills.isEmpty(), "There should be no deposit account bills after processing matured deposits");
        }

        /**
         * Tests the calculateDaysBetween() method.
         */
        @Test
        void testCalculateDaysBetween() {
            long days = stringDateConvert.calculateDaysBetween("2023-08-03", "2024-05-01");
            assertEquals(272, days, "Days between 2023-08-03 and 2024-05-01 should be 272");

            days = stringDateConvert.calculateDaysBetween("2023-01-01", "2023-12-31");
            assertEquals(364, days, "Days between 2023-01-01 and 2023-12-31 should be 364"); // 2023 is not a leap year

            days = stringDateConvert.calculateDaysBetween("2020-01-01", "2020-12-31");
            assertEquals(365, days, "Days between 2020-01-01 and 2020-12-31 should be 365"); // 2020 is a leap year
        }
    }
