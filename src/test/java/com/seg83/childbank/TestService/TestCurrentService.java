package com.seg83.childbank.TestService;

import com.seg83.childbank.gui.SwingApp;
import com.seg83.childbank.service.CurrentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestCurrentService {
    @MockBean
    private SwingApp swingApp; //avoid the GUI

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    @Autowired
    private CurrentService currentService;

/*    @Test
    public void testCheckCurrentAccountBalance() {
        assertEquals("1000", currentService.checkCurrentAccountBalance()); ;
    }*/

}
