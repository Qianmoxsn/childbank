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
    @Autowired
    private CurrentAccountDao currentAccountDao;

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