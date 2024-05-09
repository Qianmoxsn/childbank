package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;
import com.seg83.childbank.entity.HistoryActions;
import com.seg83.childbank.utils.StringDateConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository bean for accessing and manipulating history actions data
 */
@Repository
@Slf4j
public class HistoryActionsDao extends AbstractArrayDao {
    /**
     * The HistoryDao instance used for accessing history data
     */
    private final HistoryDao historyDao;
    /**
     * Utility for converting between String and Date formats
     */
    private final StringDateConvert convert;
    /**
     * The count of elements in the history actions array
     */
    long ElementCount;

    /**
     * Constructs a new HistoryActionsDao with the given HistoryDao and StringDateConvert instances
     * @param historyDao The HistoryDao instance to use
     * @param convert The StringDateConvert utility instance
     */

    @Autowired
    public HistoryActionsDao(HistoryDao historyDao, StringDateConvert convert) {
        this.historyDao = historyDao;
        this.convert = convert;
        this.getElementCount();
    }

    /**
     * Loads the history actions data in JSON format
     * @return A JSONArray containing the history actions data
     */

    @Override
    JSONArray load() {
        log.info("Request history actions data in JSON format");
//        JSONObject historyActions = historyDao.load().getJSONObject("historyActions");
        JSONArray historyActions = historyDao.load().getJSONArray("historyActions");
        log.debug("Get history actions data {}", historyActions);
        return historyActions;
    }

    /**
     * Retrieves the count of elements in the history actions array
     */

    @Override
    void getElementCount() {
        log.info("Request history actions count");
        this.ElementCount = this.load().size();
        log.debug("Get history actions count {}", this.ElementCount);
    }

    /**
     * Gets a HistoryActions object by its history ID
     * @param historyId the id of the object The ID of the history action to retrieve
     * @return The HistoryActions object with the specified ID
     */

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

    /**
     * Sets an attribute of a history action by its history ID
     * @param attrname the name of the attribute to set
     * @param value    The new value for the attribute
     * @param historyId       the id of the history action
     */

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

    /**
     * Sets the history date time for a history action
     * @param historyId The ID of the history action
     * @param value The new date time value
     */

    private void setHistoryDateTime(long historyId, Date value) {

    }

    /**
     * Sets the history amount for a history action
     * @param historyId The ID of the history action
     * @param value The new amount value
     */

    private void setHistoryAmount(long historyId, double value) {

    }

    /**
     * Sets the history type for a history action
     * @param historyId The ID of the history action
     * @param value The new type value
     */

    private void setHistoryType(long historyId, String value) {
    }

    /**
     * Creates a new history action and adds it to the history actions array
     * @param datetime The date and time of the history action
     * @param amount   The amount associated with the history action
     * @param type The type of the history action
     */

    public void createHistoryAction(Date datetime, double amount, String type) {
        log.info("Create history action with date time {}, amount {}, type {}", datetime, amount, type);
        HistoryActions newHistoryAction = new HistoryActions(this.ElementCount + 1, this.convert.DateToString(datetime), amount, type);
        this.ElementCount++;
        log.debug("History action created {}", newHistoryAction);

        List<HistoryActions> historyActions = this.load().toJavaList(HistoryActions.class);
        historyActions.add(newHistoryAction);
        log.debug("Set HistoryActions Array {}", historyActions);
        this.historyDao.setAttribute("historyActions", historyActions);
    }

    /**
     * Gets an attribute of a history action by its history ID
     * @param attrname the name of the attribute to retrieve
     * @param historyId       the id of the element
     * @return The value of the attribute
     * @throws RuntimeException If the history ID is out of range or an invalid attribute name is provided
     */

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

    /**
     * Gets the history date time for a history action
     * @param historyId The ID of the history action
     * @return The date and time of the history action
     */

    private Object getHistoryDateTime(long historyId) {
        log.info("Request history date time for history id {}", historyId);
        Date historyDateTime = this.convert.StringToDate(this.getElementById(historyId).getHistoryDateTime());
        log.debug("Get history date time {}", historyDateTime);
        return historyDateTime;
    }

    /**
     * Gets the history amount for a history action
     * @param historyId The ID of the history action
     * @return The amount associated with the history action
     */

    private Object getHistoryAmount(long historyId) {
        log.info("Request history amount for history id {}", historyId);
        double historyAmount = this.getElementById(historyId).getHistoryAmount();
        log.debug("Get history amount {}", historyAmount);
        return historyAmount;
    }

    /**
     * Gets the history type for a history action
     * @param historyId The ID of the history action
     * @return The type of the history action
     */

    private Object getHistoryType(long historyId) {
        log.info("Request history type for history id {}", historyId);
        String historyType = this.getElementById(historyId).getHistoryType();
        log.debug("Get history type {}", historyType);
        return historyType;
    }

    /**
     * Gets all attributes of all history actions
     * @return A list containing all attributes of all history actions
     */

    //TODO: Implement this method
    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}
