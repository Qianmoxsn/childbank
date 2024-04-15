package com.seg83.childbank.dao;

import com.seg83.childbank.entity.CurrentAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrentAccountDao {
    private final AccountDao accountDao;

    @Autowired
    public CurrentAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public CurrentAccount getCurrentAccount() {
        CurrentAccount currentAccount = accountDao.getAccountJson().getObject("currentAccount", CurrentAccount.class);
        log.info("Request current account data in upper json object {}", accountDao.getAccountJson());
        log.info("Get Current Account data: {}", currentAccount);
        return currentAccount;
    }

    public String getCurrentAccountAmount() {
        String currentAccountAmount = accountDao.getAccountJson().getJSONObject("currentAccount").getString("currentAccountAmount");
        log.info("Request current account amount");
        log.info("Get Current Account amount: {}", currentAccountAmount);
        return currentAccountAmount;
    }

    public String getCurrentAccountRate() {
        String currentAccountRate = accountDao.getAccountJson().getJSONObject("currentAccount").getString("currentAccountRate");
        log.info("Request current account rate");
        log.info("Get Current Account rate: {}", currentAccountRate);
        return currentAccountRate;
    }
}
