package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.CurrentAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Marks this class as a Spring Repository bean and enables SLF4J logging
 */

@Repository
@Slf4j
public class CurrentAccountDao extends AbstractDao {
    /**
     * The AccountDao instance used for accessing account data.
     */
    private final AccountDao accountDao;

    /**
     * Constructs a new CurrentAccountDao with the given AccountDao instance
     * @param accountDao The AccountDao to use for accessing account data
     */

    @Autowired
    public CurrentAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Loads the current account data as a JSON object
     * @return The current account data as a JSONObject
     */

    @Override
    public JSONObject load() {
        log.info("Request current account data in JSON format");
        JSONObject currentAccount = accountDao.load().getJSONObject("currentAccount");
        log.debug("Get current account data {}", currentAccount);
        return currentAccount;
    }

    /**
     * Sets an attribute of the current account
     * @param attrname the name of the attribute
     * @param value    the value of the attribute
     */

    @Override
    public void setAttribute(String attrname, Object value) {
        CurrentAccount modifiedCurrentAccount;
        switch (attrname) {
            case "currentAccountAmount" -> {
                log.info("Setting current account amount to {}", value);
                modifiedCurrentAccount = this.setCurrentAccountAmount((Double) value);
            }
            case "currentAccountRate" -> {
                log.info("Setting current account rate to {}", value);
                modifiedCurrentAccount = this.setCurrentAccountRate((Double) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
        accountDao.setAttribute("currentAccount", modifiedCurrentAccount);
    }

    /**
     * Sets the current account amount
     * @param value The new account amount
     * @return The modified CurrentAccount instance
     */

    private CurrentAccount setCurrentAccountAmount(Double value) {
        CurrentAccount currentAccount = this.load().toJavaObject(CurrentAccount.class);
        currentAccount.setCurrentAccountAmount(value);
        return currentAccount;
    }

    /**
     * Sets the current account rate
     * @param value The new account rate
     * @return The modified CurrentAccount instance
     */

    private CurrentAccount setCurrentAccountRate(Double value) {
        CurrentAccount currentAccount = this.load().toJavaObject(CurrentAccount.class);
        currentAccount.setCurrentAccountRate(value);
        return currentAccount;
    }

    /**
     * Gets an attribute of the current account
     * @param attrname the name of the attribute to get
     * @return The value of the attribute
     */

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "currentAccountAmount" -> this.getCurrentAccountAmount();
            case "currentAccountRate" -> this.getCurrentAccountRate();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    /**
     * Gets the current account amount
     * @return The current account amount
     */

    private double getCurrentAccountAmount() {
        log.info("Request current account amount");
        double currentAccountAmount = this.load().getDouble("currentAccountAmount");
        log.debug("Get Current Account amount: {}", currentAccountAmount);
        return currentAccountAmount;
    }

    /**
     * Gets the current account rate
     * @return The current account rate
     */

    private double getCurrentAccountRate() {
        log.info("Request current account rate");
        double currentAccountRate = this.load().getDouble("currentAccountRate");
        log.debug("Get Current Account rate: {}", currentAccountRate);
        return currentAccountRate;
    }

    /**
     * Gets all attributes of the current account as a list of objects
     * @return A list containing all attributes of the current account
     */

    @Override
    public List<Object> getAllAttributes() {
        log.info("Request all attributes of current account");
        List<Object> objectList = List.of(getAttribute("currentAccountAmount"), getAttribute("currentAccountRate"));
        log.debug("Get all attributes of current account {}", objectList);
        return objectList;
    }
}