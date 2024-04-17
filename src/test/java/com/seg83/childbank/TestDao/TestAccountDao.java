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

import java.nio.file.Path;

import static com.seg83.childbank.utils.FileDuplicator.restoreFile;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class TestAccountDao {
    private static final Path template = Path.of("src/main/resources/data_template.json5");
    private static final Path copy = Path.of("src/test/temp/data_template_test.json5");

    @MockBean
    private SwingApp swingApp; //avoid the GUI
    @Autowired
    private AdminDao adminDao;
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
        log.info("Restoring :: Write back template json\n");
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