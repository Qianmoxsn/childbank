package com.seg83.childbank.TestDao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.dao.AdminDao;
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
public class TestAdminDao {
    @MockBean
    private SwingApp swingApp; //avoid the GUI

    /**
     * The {@link AdminDao} instance to be tested
     */
    @Autowired
    private AdminDao adminDao;

    /**
     * Sets up the test environment before all tests are run.
     * Copies the data template file to a temporary location for testing purposes.
     */
    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Restores the original data template file after each test
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
     * Tests the loading of admin data from a JSON file.
     * Verifies that the loaded data matches the expected values.
     */
    @Test
    void testLoad() {
        log.info("Testing :: Load Admin Data in JSON format");
        JSONObject admin = adminDao.load();

        JSONObject target = new JSONObject();
        target.put("adminPassword", "114514");
        target.put("firstLogin", true);

        assertEquals(target, admin);
    }

    /**
     * Tests getting the admin password from the {@link AdminDao}.
     * Verifies that the retrieved password matches the expected value.
     */
    @Test
    void testGetAdminPassword() {
        log.info("Testing :: getAdminPassword");
        String password = (String) adminDao.getAttribute("adminPassword");

        assertEquals("114514", password);
    }

    /**
     * Tests setting the admin password in the {@link AdminDao}.
     * Verifies that the new password can be successfully set and retrieved.
     */
    @Test
    void testSetAdminPassword() {
        log.info("Testing :: changeAdminPassword");
        adminDao.setAttribute("adminPassword", "123456");

        String target = "123456";

        assertEquals(target, adminDao.getAttribute("adminPassword"));
    }
}