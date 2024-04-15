package com.seg83.childbank.TestDao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.dao.AccountDao;
import com.seg83.childbank.gui.SwingApp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestAccountDao {

    @MockBean
    private SwingApp swingApp; //avoid the GUI

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    @Autowired
    private AccountDao accountDao;

    @Test
    public void testGetAccount() {
        accountDao.getAccount();
    }

    @Test
    public void testGetAccountJson() {
        JSONObject jsonObject = accountDao.getAccountJson();
        assertNotNull(jsonObject);
        assertTrue(jsonObject.containsKey("currentAccount"));
        assertTrue(jsonObject.containsKey("depositAccount"));
    }
}
