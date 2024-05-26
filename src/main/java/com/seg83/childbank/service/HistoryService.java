package com.seg83.childbank.service;

import com.seg83.childbank.dao.HistoryActionsDao;
import com.seg83.childbank.dao.HistoryDao;
import com.seg83.childbank.entity.HistoryActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * HistoryService provides methods for managing operation history and related operations.
 */
@Service
@Slf4j
public class HistoryService {

    /**
     * HistoryDao instance for handling history data access.
     */
    private final HistoryDao historyDao;

    /**
     * HistoryActionsDao instance for handling history actions data access.
     */
    private final HistoryActionsDao historyActionsDao;

    /**
     * Constructor for creating a new HistoryService instance.
     *
     * @param historyDao      HistoryDao instance
     * @param historyActionsDao HistoryActionsDao instance
     */
    @Autowired
    public HistoryService(HistoryDao historyDao, HistoryActionsDao historyActionsDao) {
        this.historyDao = historyDao;
        this.historyActionsDao = historyActionsDao;
    }

    /**
     * Generates a list of operation history records for display in a JTable.
     *
     * @return a 2D array of objects representing the operation history records
     */
    public Object[][] generateHistoryList() {
        log.info("Generating History List...");
        List<HistoryActions> historyActionsList = (List<HistoryActions>) historyDao.getAttribute("historyActions");

        // For swing JTable
        Object[][] data = new Object[historyActionsList.size()][4];

        for (int i = 0; i < historyActionsList.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = historyActionsDao.getAttribute("historyDateTime", i + 1);
            data[i][2] = historyActionsDao.getAttribute("historyType", i + 1);
            data[i][3] = historyActionsDao.getAttribute("historyAmount", i + 1);
        }
        return data;
    }

    /**
     * Creates a new operation history record.
     *
     * @param amount        the amount associated with the operation
     * @param type         the type of operation (e.g., deposit, withdrawal, etc.)
     */
    public void createOperationHistory(double amount, String type) {
        Date datetime = new Date();
        log.info("Creating Operation History @ {} for {}", datetime, type);
        historyActionsDao.createHistoryAction(datetime, amount, type);
    }
}