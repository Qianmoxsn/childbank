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
    void load() {
    }

    @Test
    void setAttribute() {
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
    void getAllAttributes() {
    }
}