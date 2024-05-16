package com.seg83.childbank.TestService;

import com.seg83.childbank.dao.CurrentAccountDao;
import com.seg83.childbank.dao.HistoryActionsDao;
import com.seg83.childbank.entity.HistoryActions;
import com.seg83.childbank.service.CurrentService;
import com.seg83.childbank.service.InterestService;
import com.seg83.childbank.utils.StringDateConvert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class TestInterestService {
    @Autowired
    private InterestService interestService;

    @Autowired
    private CurrentAccountDao currentAccountDao;

    @Autowired
    private HistoryActionsDao historyActionsDao;

    @Autowired
    private StringDateConvert convert;

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


//    @Test
//    void testDailyCurrentInterest() {
//        // Set the current account rate and amount
//        currentAccountDao.setAttribute("currentAccountRate", 1.0);
//        currentAccountDao.setAttribute("currentAccountAmount", 100.0);
//
//        // Calculate the interest
//        interestService.dailyCurrentInterest();
//
//        // Check the current account balance
//        assertEquals(101.0, currentAccountDao.getAttribute("currentAccountAmount"));
//    }

    @Test
    void testCalculateCurrentInterest() {
        // Set the current account rate and amount
        currentAccountDao.setAttribute("currentAccountRate", 1.0);
        currentAccountDao.setAttribute("currentAccountAmount", 100.0);

        // Assume the last interest date is 2days before the current date
        Date currentDate = new Date();
        Date lastInterestDate = new Date(currentDate.getTime() - 2 * 24 * 60 * 60 * 1000);
        currentAccountDao.setAttribute("lastInterestDate", convert.DateToString(lastInterestDate));

        // Calculate the interest
        interestService.calculateCurrentInterest();

        // Check the current account balance
        assertEquals(102.01, currentAccountDao.getAttribute("currentAccountAmount"));

        // Check lastInterestDate
        assertEquals(convert.DateToString(currentDate), currentAccountDao.getAttribute("lastInterestDate"));

        // Check history(contains HistoryActions(historyId=6, historyAmount=1.01, historyType=daily interest))
        assertEquals(1.01, historyActionsDao.getAttribute("historyAmount", 6));
        assertEquals("daily interest", historyActionsDao.getAttribute("historyType", 6));

    }

}
