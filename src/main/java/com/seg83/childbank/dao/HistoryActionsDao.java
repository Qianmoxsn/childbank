package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;
import com.seg83.childbank.entity.HistoryActions;
import com.seg83.childbank.utils.StringDateConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class HistoryActionsDao extends AbstractArrayDao {
    private final HistoryDao historyDao;
    private final StringDateConvert convert;
    long ElementCount;

    @Autowired
    public HistoryActionsDao(HistoryDao historyDao, StringDateConvert convert) {
        this.historyDao = historyDao;
        this.convert = convert;
        this.getElementCount();
    }

    @Override
    JSONArray load() {
        log.info("Request history actions data in JSON format");
//        JSONObject historyActions = historyDao.load().getJSONObject("historyActions");
        JSONArray historyActions = historyDao.load().getJSONArray("historyActions");
        log.debug("Get history actions data {}", historyActions);
        return historyActions;
    }

    @Override
    void getElementCount() {
        log.info("Request history actions count");
        this.ElementCount = this.load().size();
        log.debug("Get history actions count {}", this.ElementCount);
    }

    @Override
    HistoryActions getElementById(long historyId) {
        log.info("Request historyAction with id {}", historyId);
        List<HistoryActions> historyActions = this.load().toList(HistoryActions.class);
        for (HistoryActions action : historyActions) {
            if (action.getHistoryId() == historyId) {
                log.debug("Get historyActions {}", action);
                return action;
            }
        }
        throw new RuntimeException("Invalid Id"); // If historyId is not found
    }

    @Override
    void setAttribute(String attrname, Object value, long historyId) {
        switch (attrname) {
            case "historyDateTime" -> {
                log.info("Set history date time to {}", value);
                this.setHistoryDateTime(historyId, (Date) value);
            }
            case "historyAmount" -> {
                log.info("Set history amount to {}", value);
                this.setHistoryAmount(historyId, (double) value);
            }
            case "historyType" -> {
                log.info("Set history type to {}", value);
                this.setHistoryType(historyId, (String) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
    }

    private void setHistoryDateTime(long historyId, Date value) {

    }

    private void setHistoryAmount(long historyId, double value) {

    }

    private void setHistoryType(long historyId, String value) {
    }

    public void createHistoryAction(Date datetime, double amount, String type) {
        log.info("Create history action with date time {}, amount {}, type {}", datetime, amount, type);
        HistoryActions newHistoryAction = new HistoryActions(this.ElementCount+1, this.convert.DateToString(datetime), amount, type);
        this.ElementCount++;
        log.debug("History action created {}", newHistoryAction);

        List<HistoryActions> historyActions = this.load().toJavaList(HistoryActions.class);
        historyActions.add(newHistoryAction);
        log.debug("Set HistoryActions Array {}", historyActions);
        this.historyDao.setAttribute("historyActions", historyActions);
    }

    @Override
    public Object getAttribute(String attrname, long historyId) {
        if (historyId < 0 || historyId > this.ElementCount) {
            throw new RuntimeException("Invalid Id range");
        }
        return switch (attrname) {
            case "historyDateTime" -> this.getHistoryDateTime(historyId);
            case "historyAmount" -> this.getHistoryAmount(historyId);
            case "historyType" -> this.getHistoryType(historyId);
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    private Object getHistoryDateTime(long historyId) {
        log.info("Request history date time for history id {}", historyId);
        Date historyDateTime = this.convert.StringToDate(this.getElementById(historyId).getHistoryDateTime());
        log.debug("Get history date time {}", historyDateTime);
        return historyDateTime;
    }

    private Object getHistoryAmount(long historyId) {
        log.info("Request history amount for history id {}", historyId);
        double historyAmount = this.getElementById(historyId).getHistoryAmount();
        log.debug("Get history amount {}", historyAmount);
        return historyAmount;
    }

    private Object getHistoryType(long historyId) {
        log.info("Request history type for history id {}", historyId);
        String historyType = this.getElementById(historyId).getHistoryType();
        log.debug("Get history type {}", historyType);
        return historyType;
    }

    //TODO: Implement this method
    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}
