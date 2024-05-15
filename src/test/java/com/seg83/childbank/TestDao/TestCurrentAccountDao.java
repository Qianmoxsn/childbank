package com.seg83.childbank.TestDao;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.gui.SwingApp;
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
public class TestCurrentAccountDao {
    @MockBean
    private SwingApp swingApp; //avoid the GUI
    /**
     * Instance of {@link CurrentAccountDao} to be tested.
     */
    @Autowired
    private CurrentAccountDao currentAccountDao;
    /**
     * Sets up the testing environment before all tests are run.
     * Copies the data template file to a temporary location for use in tests.
     */

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }
    /**
     * Restores the original data template file after each test.
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
     * Tests the retrieval of the current account amount from the DAO.
     */

    @Test
    public void testGetCurrentAccountAmount() {
        log.info("Testing :: get current account amount");
        double amount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        assertEquals(1000.0, amount);
    }

    /**
     * Tests the retrieval of the current account rate from the DAO
     */

    @Test
    public void testGetCurrentAccountRate() {
        log.info("Testing :: get current account rate");
        Double rate = (Double) currentAccountDao.getAttribute("currentAccountRate");
        assertEquals(0.3, rate);
    }
    /**
     * Tests setting the current account amount in the DAO.
     */

    @Test
    public void testSetCurrentAccountAmount() {
        log.info("Testing :: set current account amount");
        currentAccountDao.setAttribute("currentAccountAmount", 2000.0);
        assertEquals(2000.0, (Double) currentAccountDao.getAttribute("currentAccountAmount"));
    }
    /**
     * Tests setting the current account amount in the DAO.
     */

    @Test
    public void testSetCurrentAccountRate() {
        log.info("Testing :: set current account rate");
        currentAccountDao.setAttribute("currentAccountRate", 0.4);
        assertEquals(0.4, (Double) currentAccountDao.getAttribute("currentAccountRate"));
    }

    @Test
    public void testGetLastInterestDate() {
        log.info("Testing :: get last interest date");
        assertEquals("2024-05-14 12:34:56", (String) currentAccountDao.getAttribute("lastInterestDate"));
    }

    @Test
    public void testSetLastInterestDate() {
        log.info("Testing :: set last interest date");
        currentAccountDao.setAttribute("lastInterestDate", "2021-01-01 11:11:11");
        assertEquals("2021-01-01 11:11:11", (String) currentAccountDao.getAttribute("lastInterestDate"));
    }
}