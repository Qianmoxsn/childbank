package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
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

    @Autowired
    public HistoryActionsDao(HistoryDao historyDao, StringDateConvert convert) {
        this.historyDao = historyDao;
        this.convert = convert;
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
    void setAttribute(String attrname, Object value) {

    }

    @Override
    Object getAttribute(String attrname) {
        throw new RuntimeException("Id is required for history actions, use getAttribute(String attrname, long historyId) instead");
    }

    public Object getAttribute(String attrname, long historyId) {
        return switch (attrname) {
            case "historyDateTime" -> this.getHistoryDateTime(historyId);
            case "historyAmount" -> this.getHistoryAmount(historyId);
            case "historyType" -> this.getHistoryType(historyId);
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    private HistoryActions getHistoryActionById(long historyId) {
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

    private Object getHistoryDateTime(long historyId) {
        log.info("Request history date time for history id {}", historyId);
        Date historyDateTime = this.convert.StringToDate(this.getHistoryActionById(historyId).getHistoryDateTime());
        log.debug("Get history date time {}", historyDateTime);
        return historyDateTime;
    }

    private Object getHistoryAmount(long historyId) {
        log.info("Request history amount for history id {}", historyId);
//        double historyAmount = this.getHistoryActionById(historyId).getDouble("historyAmount");
        double historyAmount = 0;
        log.debug("Get history amount {}", historyAmount);
        return historyAmount;
    }

    private Object getHistoryType(long historyId) {
        log.info("Request history type for history id {}", historyId);
//        String historyType = this.load().getJSONObject(String.valueOf(historyId)).getString("historyType");
        String historyType = null;
        log.debug("Get history type {}", historyType);
        return historyType;
    }

    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}
