package com.seg83.childbank.TestService;

import com.seg83.childbank.dao.DepositAccountBillDao;
import com.seg83.childbank.service.DepositService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class TestDepositService {
    @Autowired
    private DepositService depositService;
    @Autowired
    private DepositAccountBillDao depositAccountBillDao;
    /**
     * Sets up the environment before all tests by copying a template JSON file.
     * This ensures a clean slate for each test run.
     */
    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Restores the original template JSON file after each test to maintain data integrity.
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

    @Test
    void generateDepositList() {
        System.out.println(depositService.generateDepositList());
    }

    @Test
    void createDepositAccountBill() {
        depositService.createDepositAccountBill(100,0.1,"2024-08-03");

        depositAccountBillDao.getAttribute("depositAccountBillAmount", 2);

    }
}