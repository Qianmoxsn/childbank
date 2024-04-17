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

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
        // copy the test json file to the copy
        restoreFile(template, copy);
    }

    @AfterEach
    void restoreTestJson() {
        restoreFile(copy, template);
        log.info("Restoring :: Write back template json\n");
    }

    @Autowired
    private CurrentService currentService;


    @Test
    void checkCurrentAccountBalance() {
        //TODO: test the checkCurrentAccountBalance method
    }

    @Test
    void depositCurrentAccount() {
        currentService.depositCurrentAccount(100);
        assertEquals(1100.0, currentAccountDao.getAttribute("currentAccountAmount"));
    }

    @Test
    void withdrawCurrentAccount() {
        currentService.withdrawCurrentAccount(100);
        assertEquals(900.0, currentAccountDao.getAttribute("currentAccountAmount"));
    }

    @Test
    void testToUiContent() {
        assertEquals("$1000.00", currentService.toUiContent());
    }
}
