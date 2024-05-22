package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.DepositAccount;
import com.seg83.childbank.entity.DepositAccountBills;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class DepositAccountDao extends AbstractDao {
    private final AccountDao accountDao;

    @Autowired
    public DepositAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public JSONObject load() {
        log.info("Request DepositAccountDao in JSON format");
        JSONObject depositAccount = accountDao.load().getJSONObject("depositAccount");
        log.debug("Get depositAccount data {}", depositAccount);
        return depositAccount;
    }

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

    private DepositAccount setDepositAccountBills(List<DepositAccountBills> value) {
        DepositAccount depositAccount = this.load().toJavaObject(DepositAccount.class);
        depositAccount.setDepositAccountBills(value);
        return depositAccount;
    }

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "depositAccount" -> this.getDepositAccount();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    private List<DepositAccountBills> getDepositAccount() {
        log.info("Getting historyActions");
        List<DepositAccountBills> depositAccountBills = this.load().getJSONArray("depositAccountBills").toJavaList(DepositAccountBills.class);
        log.debug("Get depositAccountBills {}", depositAccountBills);
        return depositAccountBills;
    }

    // TODO implement
    @Override
    List<Object> getAllAttributes() {
        return null;
    }
}
