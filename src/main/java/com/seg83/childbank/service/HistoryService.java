package com.seg83.childbank.service;

import com.seg83.childbank.dao.HistoryActionsDao;
import com.seg83.childbank.dao.HistoryDao;
import com.seg83.childbank.entity.HistoryActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HistoryService {
    private final HistoryDao historyDao;
    private final HistoryActionsDao historyActionsDao;

    @Autowired
    public HistoryService(HistoryDao historyDao, HistoryActionsDao historyActionsDao) {
        this.historyDao = historyDao;
        this.historyActionsDao = historyActionsDao;
    }

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
}
