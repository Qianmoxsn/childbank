package com.seg83.childbank.TestDao;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.gui.SwingApp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestCurrentAccount {
    @MockBean
    private SwingApp swingApp; //avoid the GUI

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
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

}
