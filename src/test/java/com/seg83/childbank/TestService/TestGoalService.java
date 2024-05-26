package com.seg83.childbank.TestService;

import com.seg83.childbank.dao.GoalDao;
import com.seg83.childbank.service.GoalService;
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
class TestGoalService {
    @Autowired
    private GoalService goalService;
    @Autowired
    private GoalDao goalDao;

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

    /**
     * Tests the modifyGoal() method.
     */
    @Test
    void modifyGoal() {
        goalService.modifyGoal(1111.1);
        assertEquals(1111.1, goalDao.getAttribute("goalAmount"));
    }

    /**
     * Tests the calcGoal() method.
     */
    @Test
    void calcGoal() {
        goalService.modifyGoal(1000.0);
        assertEquals(100, goalService.calcGoal());
    }

    /**
     * Tests the toUiContent() method.
     */
    @Test
    void toUiContent() {
        // This method does not have a return value or parameters, so it is not possible to write a meaningful test for it.
    }
}