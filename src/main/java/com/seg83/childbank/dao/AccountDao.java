package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Account;
import com.seg83.childbank.entity.CurrentAccount;
import com.seg83.childbank.entity.DepositAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class AccountDao extends AbstractDao {
    private final DataWrapperDao dataWrapperDao;

    @Autowired
    public AccountDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    @Override
    public JSONObject load() {
        log.info("Request account data in JSON format");
        JSONObject account = dataWrapperDao.load().getJSONObject("account");
        log.debug("Get account data {}", account);
        return account;
    }

    @Override
    public void setAttribute(String attrname, Object value) {
        Account modifiedAccount;
        switch (attrname) {
            case "accountPassword" -> {
                log.info("Setting accountPassword to {}", value);
                modifiedAccount = this.setAccountPassword((String) value);
            }
            case "depositAccount" -> {
                log.info("Setting depositAccount to {}", value);
                modifiedAccount = this.setDepositAccount((DepositAccount) value);
            }
            case "currentAccount" -> {
                log.info("Setting depositAccount to {}", value);
                modifiedAccount = this.setCurrentAccount((CurrentAccount) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
        dataWrapperDao.setAttribute("account", modifiedAccount);
    }

    //TODO: need tests
    private Account setAccountPassword(String value) {
        Account account = this.load().toJavaObject(Account.class);
        account.setAccountPassword(value);
        return account;
    }

    private Account setDepositAccount(DepositAccount value) {
        Account account = this.load().toJavaObject(Account.class);
        account.setDepositAccount(value);
        return account;
    }

    private Account setCurrentAccount(CurrentAccount value) {
        Account account = this.load().toJavaObject(Account.class);
        account.setCurrentAccount(value);
        return account;
    }

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "depositAccount" -> this.getDepositAccount();
            case "currentAccount" -> this.getCurrentAccount();
            case "accountPassword" -> this.getAccountPassword();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    //TODO: need tests
    private String getAccountPassword() {
        log.info("Request accountPassword");
        String accountPassword = this.load().getString("accountPassword");
        log.debug("Get accountPassword {}", accountPassword);
        return accountPassword;
    }

    private CurrentAccount getCurrentAccount() {
        log.info("Request currentAccount");
        CurrentAccount currentAccount = this.load().getJSONObject("currentAccount").toJavaObject(CurrentAccount.class);
        log.debug("Get currentAccount {}", currentAccount);
        return currentAccount;
    }

    private DepositAccount getDepositAccount() {
        log.info("Request depositAccount");
        DepositAccount depositAccount = this.load().getJSONObject("depositAccount").toJavaObject(DepositAccount.class);
        log.debug("Get depositAccount {}", depositAccount);
        return depositAccount;
    }

    @Override
    public List<Object> getAllAttributes() {
        log.info("Request all attributes of account");
        List<Object> objectList = List.of(getAttribute("depositAccount"), getAttribute("currentAccount"));
        log.debug("Get all attributes of account {}", objectList);
        return objectList;
    }
}