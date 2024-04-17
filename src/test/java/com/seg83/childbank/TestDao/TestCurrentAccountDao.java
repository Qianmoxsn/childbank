package com.seg83.childbank.TestDao;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.gui.SwingApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.file.Path;

import static com.seg83.childbank.utils.FileDuplicator.restoreFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestCurrentAccountDao {
    private static final Path template = Path.of("src/main/resources/data_template.json5");
    private static final Path copy = Path.of("src/test/temp/data_template_test.json5");

    @MockBean
    private SwingApp swingApp; //avoid the GUI

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
        // copy the test json file to the copy
        restoreFile(template, copy);
    }

    @Autowired
    private CurrentAccountDao currentAccountDao;

    @Test
    public void testGetCurrentAccount() {
        assertNotNull(currentAccountDao.getCurrentAccount());
    }

    @Test
    public void testGetCurrentAccountAmount() {
        String amount = currentAccountDao.getCurrentAccountAmount();
        assertEquals("1000", amount);
    }

    @Test
    public void testGetCurrentAccountRate() {
        String rate=currentAccountDao.getCurrentAccountRate();
        assertEquals("0.3", rate);
    }

    @Test
    public void testSetCurrentAccountAmount() {
        currentAccountDao.setCurrentAccountAmount("2000");
        assertEquals("2000", currentAccountDao.getCurrentAccountAmount());
    }

    @Test
    public void testSetCurrentAccountRate() {
        currentAccountDao.setCurrentAccountRate("0.4");
        assertEquals("0.4", currentAccountDao.getCurrentAccountRate());
    }

    @AfterEach
    void restoreTestJson() {
        restoreFile(copy, template);
        System.out.println("Restoring :: Write back template json\n");
    }

}
