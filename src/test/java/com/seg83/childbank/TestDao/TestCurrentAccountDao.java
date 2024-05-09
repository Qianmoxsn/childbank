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

import java.nio.file.Path;

import static com.seg83.childbank.utils.FileDuplicator.restoreFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class TestCurrentAccountDao {
    /**
     * Path to the data template file used for testing.
     */
    private static final Path template = Path.of("src/main/resources/data_template.json5");
    /**
     * Path to the temporary copy of the data template file created for testing purposes.
     */
    private static final Path copy = Path.of("src/test/temp/data_template_test.json5");
    /**
     * Path to the temporary copy of the data template file created for testing purposes.
     */

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
        // copy the test json file to the copy
        restoreFile(template, copy);
    }
    /**
     * Restores the original data template file after each test.
     */

    @AfterEach
    void restoreTestJson() {
        restoreFile(copy, template);
        System.out.println("Restoring :: Write back template json\n");
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
}