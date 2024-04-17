package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.CurrentAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class CurrentAccountDao extends AbstractDao {
    private final AccountDao accountDao;

    @Autowired
    public CurrentAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public JSONObject load() {
        log.info("Request current account data in JSON format");
        JSONObject currentAccount = accountDao.load().getJSONObject("currentAccount");
        log.debug("Get current account data {}", currentAccount);
        return currentAccount;
    }

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

    private CurrentAccount setCurrentAccountAmount(Double value) {
        CurrentAccount currentAccount = this.load().toJavaObject(CurrentAccount.class);
        currentAccount.setCurrentAccountAmount(value);
        return currentAccount;
    }

    private CurrentAccount setCurrentAccountRate(Double value) {
        CurrentAccount currentAccount = this.load().toJavaObject(CurrentAccount.class);
        currentAccount.setCurrentAccountRate(value);
        return currentAccount;
    }

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "currentAccountAmount" -> this.getCurrentAccountAmount();
            case "currentAccountRate" -> this.getCurrentAccountRate();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    private double getCurrentAccountAmount() {
        log.info("Request current account amount");
        double currentAccountAmount = this.load().getDouble("currentAccountAmount");
        log.debug("Get Current Account amount: {}", currentAccountAmount);
        return currentAccountAmount;
    }

    private double getCurrentAccountRate() {
        log.info("Request current account rate");
        double currentAccountRate = this.load().getDouble("currentAccountRate");
        log.debug("Get Current Account rate: {}", currentAccountRate);
        return currentAccountRate;
    }

    @Override
    public List<Object> getAllAttributes() {
        log.info("Request all attributes of current account");
        List<Object> objectList = List.of(getAttribute("currentAccountAmount"), getAttribute("currentAccountRate"));
        log.debug("Get all attributes of current account {}", objectList);
        return objectList;
    }
}