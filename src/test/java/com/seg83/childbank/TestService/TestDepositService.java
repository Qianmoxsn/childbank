package com.seg83.childbank.TestService;

import com.seg83.childbank.dao.DepositAccountBillsDao;
import com.seg83.childbank.service.DepositService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class TestDepositService {
    @Autowired
    private DepositService depositService;
    @Autowired
    private DepositAccountBillsDao depositAccountBillsDao;

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    @AfterEach
    void restoreTestJson() {
        try {
            Files.deleteIfExists(Path.of("data.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Remove :: test data json\n");
    }

    @Test
    void generateDepositList() {
        Object[][] depositList = depositService.generateDepositList();
        assertNotNull(depositList, "Deposit list should not be null");
        assertTrue(depositList.length > 0, "Deposit list should not be empty");
        System.out.println(Arrays.deepToString(depositList));
    }

    @Test
    void createDepositAccountBill() {
        long initialElementCount = depositAccountBillsDao.ElementCount;

        depositService.createDepositAccountBill(100, 0.1, "2024-08-03");

        long newElementCount = depositAccountBillsDao.ElementCount;
        assertEquals(initialElementCount + 1, newElementCount, "Element count should have incremented by 1");

        Object amount = depositAccountBillsDao.getAttribute("depositAccountBillAmount", newElementCount);
        assertNotNull(amount, "Deposit account bill amount should not be null");
        assertEquals(100.0, amount, "Deposit account bill amount should be 100.0");

        Object rate = depositAccountBillsDao.getAttribute("depositAccountBillRate", newElementCount);
        assertNotNull(rate, "Deposit account bill rate should not be null");
        assertEquals(0.1, rate, "Deposit account bill rate should be 0.1");

        Object expireDate = depositAccountBillsDao.getAttribute("depositAccountBillExpireDate", newElementCount);
        assertNotNull(expireDate, "Deposit account bill expire date should not be null");
        assertEquals("2024-08-03", expireDate, "Deposit account bill expire date should be '2024-08-03'");
    }
}
