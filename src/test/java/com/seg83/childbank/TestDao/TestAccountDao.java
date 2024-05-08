package com.seg83.childbank.TestDao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.dao.AdminDao;
import com.seg83.childbank.dao.DataWrapperDao;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class TestAccountDao {
    @MockBean
    private SwingApp swingApp; //avoid the GUI
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private DataWrapperDao dataWrapperDao;

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
    void testLoad() {
        log.info("Testing :: Load Account Data in JSON format");
        JSONObject account = dataWrapperDao.load();

        assertNotNull(account);
        assertTrue(account.containsKey("account"));
        assertTrue(account.getJSONObject("account").containsKey("depositAccount"));
        assertTrue(account.getJSONObject("account").containsKey("currentAccount"));
        assertTrue(account.getJSONObject("account").getJSONObject("currentAccount").containsKey("currentAccountRate"));
    }

    //TODO: Test non-static methods
}