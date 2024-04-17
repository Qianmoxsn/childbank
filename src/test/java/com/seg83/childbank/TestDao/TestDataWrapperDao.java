package com.seg83.childbank.TestDao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.dao.DataWrapperDao;
import com.seg83.childbank.entity.Account;
import com.seg83.childbank.entity.Admin;
import com.seg83.childbank.entity.CurrentAccount;
import com.seg83.childbank.entity.DepositAccount;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TestDataWrapperDao {
    private static final Path template = Path.of("src/main/resources/data_template.json5");
    private static final Path copy = Path.of("src/test/temp/data_template_test.json5");

    @MockBean
    private SwingApp swingApp; //avoid the GUI
    @Autowired
    private DataWrapperDao dataWrapperDao;

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
    void testLoadJsonFile() throws Exception {
        System.out.println(dataWrapperDao.loadJsonFile());
    }

    @Test
    void testSaveJsonFile1() throws Exception {
        JSONObject testJson = new JSONObject();
        testJson.put("key", "val");
        dataWrapperDao.saveJsonFile(testJson,"test");
        System.out.println(dataWrapperDao.loadJsonFile().toJSONString());

        assertTrue(dataWrapperDao.loadJsonFile().toJSONString().contains("\"test\":{\"key\":\"val\"}"));
    }

    @Test
    void testSaveJsonFile2() throws Exception {
        JSONObject testJson = new JSONObject();
        testJson.put("adminPassword", "123456");
        dataWrapperDao.saveJsonFile(testJson,"admin");
        System.out.println(dataWrapperDao.loadJsonFile().toJSONString());

        assertTrue(dataWrapperDao.loadJsonFile().toJSONString().contains("\"admin\":{\"adminPassword\":\"123456\"}"));
        assertFalse(dataWrapperDao.loadJsonFile().toJSONString().contains("114514"));
    }
    @Test
    void getAttrAdmin(){
        Admin target = new Admin("114514");
        Admin admin =(Admin) dataWrapperDao.getAttribute("admin");
        log.info("Admin Data: {}", admin);
        assertEquals(target, admin);
    }

    @Test
    void getAttrAccount(){
        Account account = (Account) dataWrapperDao.getAttribute("account");
        System.out.println(account);
        assertInstanceOf(Account.class, account);
        // TODO: test more about the account
    }


}
