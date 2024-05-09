package com.seg83.childbank.TestService;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.gui.SwingApp;
import com.seg83.childbank.service.CurrentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.file.Path;

import static com.seg83.childbank.utils.FileDuplicator.restoreFile;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TestCurrentService {
    private static final Path template = Path.of("src/main/resources/data_template.json5");
    private static final Path copy = Path.of("src/test/temp/data_template_test.json5");

    @MockBean
    private SwingApp swingApp; //avoid the GUI
    @Autowired
    private CurrentAccountDao currentAccountDao;
    /**
     * Sets up the environment before all tests by copying a template JSON file.
     * This ensures a clean slate for each test run.
     */

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
        // copy the test json file to the copy
        restoreFile(template, copy);
    }
    /**
     * Restores the original template JSON file after each test to maintain data integrity.
     */

    @AfterEach
    void restoreTestJson() {
        restoreFile(copy, template);
        log.info("Restoring :: Write back template json\n");
    }
    /**
     * Placeholder for the checkCurrentAccountBalance test, which needs to be implemented.
     */

    @Autowired
    private CurrentService currentService;

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
    /**
     * Tests the toUiContent method, which converts the current account balance to a formatted string.
     */

    @Test
    void testToUiContent() {
        assertEquals("$1000.00", currentService.toUiContent());
    }
}
