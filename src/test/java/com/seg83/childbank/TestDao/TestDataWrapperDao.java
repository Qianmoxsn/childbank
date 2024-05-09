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

import java.nio.file.Path;

import static com.seg83.childbank.utils.FileDuplicator.restoreFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

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
        Admin target = new Admin("114514", true);
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