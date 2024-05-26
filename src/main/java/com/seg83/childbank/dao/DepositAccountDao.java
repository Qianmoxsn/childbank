package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.DepositAccount;
import com.seg83.childbank.entity.DepositAccountBills;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Data Access Object (DAO) for managing deposit account data.
 * Extends {@link AbstractDao} to handle deposit account data in JSON format.
 */
@Repository
@Slf4j
public class DepositAccountDao extends AbstractDao {

    private final AccountDao accountDao;

    /**
     * Constructor for DepositAccountDao.
     *
     * @param accountDao AccountDao instance for managing account data.
     */
    @Autowired
    public DepositAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Load deposit account data in JSON format.
     *
     * @return JSONObject containing deposit account data.
     */
    @Override
    public JSONObject load() {
        log.info("Request DepositAccountDao in JSON format");
        JSONObject depositAccount = accountDao.load().getJSONObject("depositAccount");
        log.debug("Get depositAccount data {}", depositAccount);
        return depositAccount;
    }

    /**
     * Set an attribute of a deposit account.
     *
     * @param attrname   Name of the attribute to set.
     * @param value     Value to set for the attribute.
     */
    @Override
    void setAttribute(String attrname, Object value) {
        DepositAccount modifiedDepositAccount;
        switch (attrname) {
            case "depositAccountBills" -> {
                log.info("Setting depositAccountBills to {}", value);
                modifiedDepositAccount = this.setDepositAccountBills((List<DepositAccountBills>) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
        this.accountDao.setAttribute("depositAccount", modifiedDepositAccount);
    }

    /**
     * Set the deposit account bills.
     *
     * @param value  List of DepositAccountBills objects to set for the deposit account.
     * @return DepositAccount object with the updated deposit account bills.
     */
    private DepositAccount setDepositAccountBills(List<DepositAccountBills> value) {
        DepositAccount depositAccount = this.load().toJavaObject(DepositAccount.class);
        depositAccount.setDepositAccountBills(value);
        return depositAccount;
    }

    /**
     * Get an attribute of a deposit account.
     *
     * @param attrname   Name of the attribute to get.
     * @return Object containing the attribute value.
     */
    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "depositAccount" -> this.getDepositAccount();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    /**
     * Get the deposit account.
     *
     * @return DepositAccount object containing the deposit account data.
     */
    private List<DepositAccountBills> getDepositAccount() {
        log.info("Getting historyActions");
        List<DepositAccountBills> depositAccountBills = this.load().getJSONArray("depositAccountBills").toJavaList(DepositAccountBills.class);
        log.debug("Get depositAccountBills {}", depositAccountBills);
        return depositAccountBills;
    }

    /**
     * Get all attributes of a deposit account.
     *
     * @return List of Objects containing all attributes of a deposit account.
     */
    @Override
    List<Object> getAllAttributes() {
        // TODO implement
        return null;
    }
}
