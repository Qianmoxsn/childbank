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
    /**
     * A mocked instance of SwingApp to avoid GUI interactions during testing.
     */
    @Autowired
    private DataWrapperDao dataWrapperDao;
    /**
     * Sets up the testing environment before all tests are run.
     * This includes copying the template JSON file to a temporary location.
     */

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }
    /**
     * Restores the template JSON file after each test to ensure consistency.
     */

    @AfterEach
    void restoreTestJson() {
        try {
            Files.deleteIfExists(Path.of("data.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Remove :: test data json\n");
    }
    /**
     * Tests the loadJsonFile method of DataWrapperDao.
     * Verifies that the JSON file can be loaded correctly.
     */

    @Test
    void testLoadJsonFile() {
        log.info("Testing :: Load Data in JSON format");
        System.out.println(dataWrapperDao.loadJsonFile());
    }
    /**
     * Tests the saveJsonFile and loadJsonFile methods of DataWrapperDao.
     * Verifies that data can be saved to and loaded from a JSON file correctly.
     */

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
    /**
     * Tests the getAttribute method of DataWrapperDao for retrieving Admin data.
     * Verifies that the Admin object can be correctly retrieved.
     */

    @Test
    void getAttrAdmin() {
        log.info("Testing :: getAttribute Admin");
        Admin target = new Admin("114514", true);
        Admin admin = (Admin) dataWrapperDao.getAttribute("admin");

        assertEquals(target, admin);
    }
    /**
     * Tests the getAttribute method of DataWrapperDao for retrieving Account data.
     * Verifies that the Account object can be correctly retrieved and is of the expected type.
     */

    @Test
    void getAttrAccount() {
        log.info("Testing :: getAttribute Account");
        Account account = (Account) dataWrapperDao.getAttribute("account");
        System.out.println(account);

        assertInstanceOf(Account.class, account);
        // TODO: test more about the account
    }
}