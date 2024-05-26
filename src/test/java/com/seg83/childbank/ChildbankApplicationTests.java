package com.seg83.childbank;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ChildbankApplicationTests is a test class for testing the ChildbankApplication class.
 */
@SpringBootTest
class ChildbankApplicationTests {

    /**
     * Sets up the test environment before each test.
     */
    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Tests if the context loads successfully.
     */
    @Test
    void contextLoads() {
        System.out.println("Test");
    }
}
