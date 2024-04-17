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
        System.out.println("Restoring :: Write back template json\n");
    }

    @Test
    public void testGetCurrentAccountAmount() {
        log.info("Testing :: get current account amount");
        double amount = (Double) currentAccountDao.getAttribute("currentAccountAmount");
        assertEquals(1000.0, amount);
    }

    @Test
    public void testGetCurrentAccountRate() {
        log.info("Testing :: get current account rate");
        Double rate = (Double) currentAccountDao.getAttribute("currentAccountRate");
        assertEquals(0.3, rate);
    }

    @Test
    public void testSetCurrentAccountAmount() {
        log.info("Testing :: set current account amount");
        currentAccountDao.setAttribute("currentAccountAmount", 2000.0);
        assertEquals(2000.0, (Double) currentAccountDao.getAttribute("currentAccountAmount"));
    }

    @Test
    public void testSetCurrentAccountRate() {
        log.info("Testing :: set current account rate");
        currentAccountDao.setAttribute("currentAccountRate", 0.4);
        assertEquals(0.4, (Double) currentAccountDao.getAttribute("currentAccountRate"));
    }
}