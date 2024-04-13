package com.seg83.childbank;

import com.seg83.childbank.gui.SwingApp;
import com.seg83.childbank.gui.component.MainFrame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.awt.*;

@SpringBootTest
class ChildbankApplicationTests {
    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    void contextLoads() {
        System.out.println("Test");
    }

}
