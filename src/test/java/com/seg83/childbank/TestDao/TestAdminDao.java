package com.seg83.childbank.TestDao;

import com.seg83.childbank.dao.AdminDao;
import com.seg83.childbank.entity.Admin;
import com.seg83.childbank.gui.SwingApp;
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
public class TestAdminDao {
    private static final Path template = Path.of("src/main/resources/data_template.json5");
    private static final Path copy = Path.of("src/test/temp/data_template_test.json5");

    @MockBean
    private SwingApp swingApp; //avoid the GUI

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
        // copy the test json file to the copy
        restoreFile(template, copy);
    }

    @Autowired
    private AdminDao adminDao;


    @Test
    void testGetAdmin() {
        System.out.println("Testing :: getAdmin");
        Admin admin = adminDao.getAdmin();

        Admin targetadmin = new Admin("114514");
        assertEquals(targetadmin, admin);
    }

    @Test
    void testGetAdminPassword() {
        System.out.println("Testing :: getAdminPassword");
        String password = adminDao.getAdminPassword();

        assertEquals("114514", password);
    }

    @Test
    void testSetAdminPassword() {
        System.out.println("Testing :: changeAdminPassword(Admin)");
        Admin admin = new Admin("1919810");
        adminDao.setAdminPassword(admin);

        Admin targetadmin = new Admin("1919810");
        assertEquals(targetadmin, adminDao.getAdmin());
    }

    @Test
    void testSetAdminPasswordString() {
        System.out.println("Testing :: changeAdminPassword(String)");
        adminDao.setAdminPassword("123456");

        Admin targetadmin = new Admin("123456");
        assertEquals(targetadmin, adminDao.getAdmin());
    }

    @AfterEach
    void restoreTestJson() {
        restoreFile(copy, template);
        System.out.println("Restoring :: Write back template json\n");

    }
}
