package com.seg83.childbank.TestDao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.dao.DataWrapperDao;
import com.seg83.childbank.entity.Account;
import com.seg83.childbank.entity.Admin;
import com.seg83.childbank.entity.DataWrapper;
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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
@Slf4j
public class TestDataWrapperDao {
    @MockBean
    private SwingApp swingApp; //avoid the GUI
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
    void testLoadJsonFile() {
        log.info("Testing :: Load Data in JSON format");
        System.out.println(dataWrapperDao.loadJsonFile());
    }

    @Test
    void testSaveJsonFile() {
        log.info("Testing :: Save Data to JSON file");
        DataWrapper testData = new DataWrapper();
        dataWrapperDao.saveJsonFile(testData);
        System.out.println(dataWrapperDao.loadJsonFile().toJSONString());

        JSONObject target = new JSONObject();

        assertInstanceOf(JSONObject.class, dataWrapperDao.load());
        assertEquals(target, dataWrapperDao.load());
    }

    @Test
    void getAttrAdmin() {
        log.info("Testing :: getAttribute Admin");
        Admin target = new Admin("114514");
        Admin admin = (Admin) dataWrapperDao.getAttribute("admin");

        assertEquals(target, admin);
    }

    @Test
    void getAttrAccount() {
        log.info("Testing :: getAttribute Account");
        Account account = (Account) dataWrapperDao.getAttribute("account");
        System.out.println(account);

        assertInstanceOf(Account.class, account);
        // TODO: test more about the account
    }
}