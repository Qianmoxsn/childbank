package com.seg83.childbank.TestDao;

import com.seg83.childbank.dao.DataWrapperDao;
import com.seg83.childbank.gui.SwingApp;
import com.seg83.childbank.gui.component.MainFrame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestDataWrapperDao {
    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    @Autowired
    private DataWrapperDao dataWrapperDao;

    @Test
    void testLoadJsonFile() throws Exception{
        System.out.println(dataWrapperDao.loadJsonFile());
    }

}
