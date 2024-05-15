package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Account;
import com.seg83.childbank.entity.CurrentAccount;
import com.seg83.childbank.entity.DepositAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The AccountDao class is a data access object (dao) that interacts with account data.
 * It provides ways to load, set up, and get information about your account.
 *
 * @author Your Name / Organization
 * @version 1.0
 */

@Repository
@Slf4j
public class AccountDao extends AbstractDao {
    /**
     * DataWrapperDao instance for data encapsulation and unencapsulation.
     */
    private final DataWrapperDao dataWrapperDao;

    /**
     * Constructor that initializes the DataWrapperDao instance by dependency injection
     *
     * @param dataWrapperDao
     */
    @Autowired
    public AccountDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    /**
     * Load the account data and convert it to a JSON object.
     *
     * @return JSON object
     */
    @Override
    public JSONObject load() {
        log.info("Request account data in JSON format");
        JSONObject account = dataWrapperDao.load().getJSONObject("account");
        log.debug("Get account data {}", account);
        return account;
    }

    /**
     * Set a property value for an account
     *
     * @param attrname the name of the attribute
     * @param value    the value of the attribute
     */
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

    /**
     * Private method to set the password of the account
     *
     * @param value New password
     * @return Updated account object
     */
    //TODO: need tests
    private Account setAccountPassword(String value) {
        Account account = this.load().toJavaObject(Account.class);
        account.setAccountPassword(value);
        return account;
    }

    /**
     * Private method for setting up a fixed deposit account for an account
     *
     * @param value new fixed deposit account
     * @return Updated account object
     */
    private Account setDepositAccount(DepositAccount value) {
        Account account = this.load().toJavaObject(Account.class);
        account.setDepositAccount(value);
        return account;
    }

    /**
     * Private method for setting up a checking account for an account
     *
     * @param value New checking account
     * @return Updated account object
     */
    private Account setCurrentAccount(CurrentAccount value) {
        Account account = this.load().toJavaObject(Account.class);
        account.setCurrentAccount(value);
        return account;
    }

    /**
     * Gets an attribute value for an account
     *
     * @param attrname the name of the attribute
     * @return attribute value
     */
    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "depositAccount" -> this.getDepositAccount();
            case "currentAccount" -> this.getCurrentAccount();
            case "accountPassword" -> this.getAccountPassword();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    /**
     * Private method for obtaining the password of the account
     *
     * @return AccountPassword
     */
    //TODO: need tests
    private String getAccountPassword() {
        log.info("Request accountPassword");
        String accountPassword = this.load().getString("accountPassword");
        log.debug("Get accountPassword {}", accountPassword);
        return accountPassword;
    }

    /**
     * Private method for obtaining the account's current deposit account
     *
     * @return Current deposit account
     */
    private CurrentAccount getCurrentAccount() {
        log.info("Request currentAccount");
        CurrentAccount currentAccount = this.load().getJSONObject("currentAccount").toJavaObject(CurrentAccount.class);
        log.debug("Get currentAccount {}", currentAccount);
        return currentAccount;
    }

    /**
     * Private method for obtaining a fixed deposit account of the account
     *
     * @return Time deposit account
     */
    private DepositAccount getDepositAccount() {
        log.info("Request depositAccount");
        DepositAccount depositAccount = this.load().getJSONObject("depositAccount").toJavaObject(DepositAccount.class);
        log.debug("Get depositAccount {}", depositAccount);
        return depositAccount;
    }

    /**
     * Gets all attribute values for the account
     *
     * @return contains a list of all property values
     */
    @Override
    public List<Object> getAllAttributes() {
        log.info("Request all attributes of account");
        List<Object> objectList = List.of(getAttribute("depositAccount"), getAttribute("currentAccount"));
        log.debug("Get all attributes of account {}", objectList);
        return objectList;
    }
}