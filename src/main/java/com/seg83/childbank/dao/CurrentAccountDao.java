package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.CurrentAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class CurrentAccountDao {
    private final AccountDao accountDao;
    private final DataWrapperDao dataWrapperDao;

    @Autowired
    public CurrentAccountDao(AccountDao accountDao, DataWrapperDao dataWrapperDao) {
        this.accountDao = accountDao;
        this.dataWrapperDao = dataWrapperDao;
    }

    public CurrentAccount getCurrentAccount() {
        CurrentAccount currentAccount = accountDao.getAccountJson().getObject("currentAccount", CurrentAccount.class);
        log.info("Request current account data in upper json object {}", accountDao.getAccountJson());
        log.info("Get Current Account data: {}", currentAccount);
        return currentAccount;
    }

    public String getCurrentAccountAmount() {
        log.info("Request current account amount");
        String currentAccountAmount = accountDao.getAccountJson().getJSONObject("currentAccount").getString("currentAccountAmount");
        log.info("Get Current Account amount: {}", currentAccountAmount);
        return currentAccountAmount;
    }

    // TODO: 越界检测
    public void setCurrentAccountAmount(String amount) {
        JSONObject jsonObject = accountDao.getAccountJson().getJSONObject("currentAccount");
        jsonObject.put("currentAccountAmount", amount);
//        accountDao.setAccountJson(jsonObject);
        log.info("Set Current Account amount: {}", amount);
    }

    public String getCurrentAccountRate() {
        log.info("Request current account rate");
        String currentAccountRate = accountDao.getAccountJson().getJSONObject("currentAccount").getString("currentAccountRate");
        log.info("Get Current Account rate: {}", currentAccountRate);
        return currentAccountRate;
    }

    public void setCurrentAccountRate(String rate) {
        JSONObject jsonObject = accountDao.getAccountJson().getJSONObject("currentAccount");
        jsonObject.put("currentAccountRate", rate);
//        accountDao.setAccountJson(jsonObject);
        log.info("Set Current Account rate: {}", rate);
    }


}
