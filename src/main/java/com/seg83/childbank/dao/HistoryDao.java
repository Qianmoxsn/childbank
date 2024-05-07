package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.History;
import com.seg83.childbank.entity.HistoryActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class HistoryDao extends AbstractDao {
    private final DataWrapperDao dataWrapperDao;

    @Autowired
    public HistoryDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    @Override
    JSONObject load() {
        log.info("Request history data in JSON format");
        JSONObject history = dataWrapperDao.load().getJSONObject("history");
        log.debug("Get history data {}", history);
        return history;
    }

    //TODO: implement
    @Override
    void setAttribute(String attrname, Object value) {
        History modifiedHistory;
        switch (attrname) {
            case "historyActions" -> {
                log.info("Setting historyActions to {}", value);
                modifiedHistory = this.setHistoryActions((List<HistoryActions>) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
        this.dataWrapperDao.setAttribute("history", modifiedHistory);
    }

    //TODO: need tests
    private History setHistoryActions(List<HistoryActions> value) {
        History history = this.load().toJavaObject(History.class);
        history.setHistoryActions(value);
        return history;
    }

    @Override
    Object getAttribute(String attrname) {
        return switch (attrname) {
            case "historyActions" -> this.getHistoryActions();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

//    public Object getAttribute(String attrname, int historyId) {
//        return switch (attrname) {
//            case "historyActions" -> this.getHistoryActions(historyId);
//            default -> throw new RuntimeException("Invalid attribute name");
//        };
//    }

    //TODO: need tests
    private List<HistoryActions> getHistoryActions() {
        log.info("Getting historyActions");
        List<HistoryActions> historyActions = this.load().getJSONObject("historyActions").toJavaObject(List.class);
        log.debug("Get historyActions {}", historyActions);
        return historyActions;
    }

    //TODO: implement
    @Override
    List<Object> getAllAttributes() {
        return null;
    }
}
