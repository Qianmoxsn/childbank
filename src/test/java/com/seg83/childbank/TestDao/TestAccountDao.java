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
    private SwingApp swingApp;

    /**
     * AdminDao dependency injected for testing.
     */
    @Autowired
    private AdminDao adminDao;

    /**
     * DataWrapperDao dependency injected for testing.
     */
    @Autowired
    private DataWrapperDao dataWrapperDao;

    /**
     * Sets up the testing environment before all tests are run.
     * Copies the data template file to a temporary location for use in tests.
     */
    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Restores the original data template file after each test.
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
     * Tests the loading of account data from a JSON file.
     * Verifies that the loaded JSON object contains the expected keys and structure.
     */
    @Test
    void testLoad() {
        log.info("Testing :: Load Account Data in JSON format");
        JSONObject account = dataWrapperDao.load();

        assertNotNull(account, "The loaded JSONObject should not be null.");
        assertTrue(account.containsKey("account"), "The loaded JSONObject should contain the 'account' key.");
        assertTrue(account.getJSONObject("account").containsKey("depositAccount"),
                "The 'account' JSONObject should contain the 'depositAccount' key.");
        assertTrue(account.getJSONObject("account").containsKey("currentAccount"),
                "The 'account' JSONObject should contain the 'currentAccount' key.");
        assertTrue(account.getJSONObject("account").getJSONObject("currentAccount").containsKey("currentAccountRate"),
                "The 'currentAccount' JSONObject should contain the 'currentAccountRate' key.");
    }

    // TODO: Add javadoc for non-static methods when they are implemented.
}