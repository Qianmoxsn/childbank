package com.seg83.childbank.TestDao;

import com.seg83.childbank.dao.HistoryActionsDao;
import com.seg83.childbank.gui.SwingApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class TestHistoryActionsDao {
    @MockBean
    private SwingApp swingApp; //avoid the GUI
    @Autowired
    private HistoryActionsDao historyActionsDao;
    /**
     * Sets up the testing environment before all tests are run.
     * This includes copying a template JSON file to be used by the tests.
     */

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }
    /**
     * Restores the template JSON file after each test to ensure consistency between tests.
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
     * Tests the retrieval of the datetime attribute for a specific history action.
     */


    @Test
    void getHistoryActionDatetime() {
        log.info("Testing :: get history action datetime");
        long historyId = 1;
        Date datetime = (Date) historyActionsDao.getAttribute("historyDateTime", historyId);
        // "2019-01-01 00:00:00"
        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.MILLISECOND, 0);
        expected.set(2019, Calendar.JANUARY, 1, 0, 0, 0);
        assertEquals(expected.getTime(), datetime);
    }
    /**
     * Tests the retrieval of the datetime attribute for a specific history action.
     */

    @Test
    void getHistoryActionAmount() {
        log.info("Testing :: get history action amount");
        long historyId = 1;
        double amount = (Double) historyActionsDao.getAttribute("historyAmount", historyId);
        assertEquals(1200.0, amount);
    }
    /**
     * Tests the retrieval of the type attribute for a specific history action.
     */

    @Test
    void getHistoryActionType() {
        log.info("Testing :: get history action type");
        long historyId = 1;
        String type = (String) historyActionsDao.getAttribute("historyType", historyId);
        assertEquals("current deposit", type);
    }
    /**
     * Tests the creation of a new history action and verifies its attributes.
     */

    @Test
    void createHistoryAction() {
        log.info("Testing :: create history action");
        Date datetime = new Date();
        //ms = 0
        datetime.setTime(0);
        double amount = 100.0;
        String type = "current deposit";
        historyActionsDao.createHistoryAction(datetime, amount, type);
        assertEquals(datetime, historyActionsDao.getAttribute("historyDateTime", 5));
        assertEquals(amount, historyActionsDao.getAttribute("historyAmount", 5));
        assertEquals(type, historyActionsDao.getAttribute("historyType", 5));
    }
}