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

import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;

import static com.seg83.childbank.utils.FileDuplicator.restoreFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
class TestHistoryActionsDao {
    private static final Path template = Path.of("src/main/resources/data_template.json5");
    private static final Path copy = Path.of("src/test/temp/data_template_test.json5");

    @MockBean
    private SwingApp swingApp; //avoid the GUI
    @Autowired
    private HistoryActionsDao historyActionsDao;

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
        // copy the test json file to the copy
        restoreFile(template, copy);
    }

    @AfterEach
    void restoreTestJson() {
        restoreFile(copy, template);
        System.out.println("Restoring :: Write back template json\n");
    }


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

    @Test
    void getHistoryActionAmount() {
        log.info("Testing :: get history action amount");
        long historyId = 1;
        double amount = (Double) historyActionsDao.getAttribute("historyAmount", historyId);
        assertEquals(100.0, amount);
    }

    @Test
    void getHistoryActionType() {
        log.info("Testing :: get history action type");
        long historyId = 1;
        String type = (String) historyActionsDao.getAttribute("historyType", historyId);
        assertEquals("current deposit", type);
    }

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