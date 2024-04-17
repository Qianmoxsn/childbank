package com.seg83.childbank;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
