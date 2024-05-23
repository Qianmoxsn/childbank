package com.seg83.childbank.TestService;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.gui.SwingApp;
import com.seg83.childbank.service.CurrentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class TestCurrentService {
    @MockBean
    private SwingApp swingApp; //avoid the GUI
    @Autowired
    private CurrentAccountDao currentAccountDao;
    @Autowired
    private CurrentService currentService;

    /**
     * Sets up the environment before all tests by copying a template JSON file.
     * This ensures a clean slate for each test run.
     */
    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Restores the original template JSON file after each test to maintain data integrity.
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
     *
     */
    @Test
    void checkCurrentAccountBalance() {
        //TODO: test the checkCurrentAccountBalance method
    }

    /**
     * Restores the original template JSON file after each test to maintain data integrity.
     */
    @Test
    void depositCurrentAccount() {
        currentService.depositCurrentAccount(100);
        assertEquals(1100.0, currentAccountDao.getAttribute("currentAccountAmount"));
    }

    /**
     * Tests the withdrawCurrentAccount method by withdrawing an amount and verifying the new balance.
     */
    @Test
    void withdrawCurrentAccount() {
        currentService.withdrawCurrentAccount(100);
        assertEquals(900.0, currentAccountDao.getAttribute("currentAccountAmount"));
    }

    @Test
    void testDailyCurrentInterest() {
        // Set the current account rate and amount
        currentAccountDao.setAttribute("currentAccountRate", 1.0);
        currentAccountDao.setAttribute("currentAccountAmount", 100.0);

        // Calculate the interest
        currentService.payDailyCurrentInterest(1.1);

        // Check the current account balance
        assertEquals(101.1, currentService.checkCurrentAccountBalance());
    }

    /**
     * Tests the toUiContent method, which converts the current account balance to a formatted string.
     */
    @Test
    void testAmountToUiContent() {
        assertEquals("$1000.00", currentService.amountToUiContent());
    }
}
