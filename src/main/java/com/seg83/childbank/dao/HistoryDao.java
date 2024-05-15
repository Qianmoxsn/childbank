package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.History;
import com.seg83.childbank.entity.HistoryActions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Represents the Data Access Object for History related operations
 */
@Repository
@Slf4j
public class HistoryDao extends AbstractDao {
    /**
     * The DataWrapperDao used for accessing the underlying data store
     */
    private final DataWrapperDao dataWrapperDao;

    /**
     * Constructs a new HistoryDao with the provided DataWrapperDao
     *
     * @param dataWrapperDao the DataWrapperDao for accessing the data store
     */
    @Autowired
    public HistoryDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    /**
     * Loads the history data in JSON format
     *
     * @return a JSONObject containing the history data
     */
    @Override
    JSONObject load() {
        log.info("Request history data in JSON format");
        JSONObject history = dataWrapperDao.load().getJSONObject("history");
        log.debug("Get history data {}", history);
        return history;
    }

    /**
     * Sets the specified attribute of the history data
     *
     * @param attrname the name of the attribute
     * @param value    the value of the attribute
     */
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

    /**
     * Helper method to set the historyActions attribute of the history data
     *
     * @param value the new list of HistoryActions
     * @return the modified History object
     */
    //TODO: need tests
    private History setHistoryActions(List<HistoryActions> value) {
        History history = this.load().toJavaObject(History.class);
        history.setHistoryActions(value);
        return history;
    }

    /**
     * Gets the value of the specified attribute from the history data
     *
     * @param attrname the name of the attribute to get
     * @return the value of the attribute
     */
    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "historyActions" -> this.getHistoryActions();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    /**
     * Helper method to get the historyActions attribute from the history data
     *
     * @return the list of HistoryActions
     */
    //TODO: need tests
    private List<HistoryActions> getHistoryActions() {
        log.info("Getting historyActions");
        List<HistoryActions> historyActions = this.load().getJSONArray("historyActions").toJavaList(HistoryActions.class);
        log.debug("Get historyActions {}", historyActions);
        return historyActions;
    }

    /**
     * Placeholder method for retrieving all attributes of the history data
     *
     * @return null
     */
    //TODO: implement
    @Override
    List<Object> getAllAttributes() {
        return null;
    }
}
