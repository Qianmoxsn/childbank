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

import java.nio.file.Path;

import static com.seg83.childbank.utils.FileDuplicator.restoreFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class TestAdminDao {
    private static final Path template = Path.of("src/main/resources/data_template.json5");
    private static final Path copy = Path.of("src/test/temp/data_template_test.json5");

    @MockBean
    private SwingApp swingApp; //avoid the GUI

    @Autowired
    private AdminDao adminDao;

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
        // copy the test json file to the copy
        restoreFile(template, copy);
    }

    @AfterEach
    void restoreTestJson() {
        restoreFile(copy, template);
        log.info("Restoring :: Write back template json\n");
    }

    @Test
    void testLoad() {
        log.info("Testing :: Load Admin Data in JSON format");
        JSONObject admin = adminDao.load();

        JSONObject target = new JSONObject();
        target.put("adminPassword", "114514");
        target.put("firstLogin", true);

        assertEquals(target, admin);
    }

    @Test
    void testGetAdminPassword() {
        log.info("Testing :: getAdminPassword");
        String password = (String) adminDao.getAttribute("adminPassword");

        assertEquals("114514", password);
    }


    @Test
    void testSetAdminPassword() {
        log.info("Testing :: changeAdminPassword");
        adminDao.setAttribute("adminPassword", "123456");

        String target = "123456";

        assertEquals(target, adminDao.getAttribute("adminPassword"));
    }
}