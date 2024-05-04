package com.seg83.childbank.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class HistoryActionsDao extends AbstractDao{
    private HistoryDao historyDao;

    @Autowired
    public HistoryActionsDao(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }
}
