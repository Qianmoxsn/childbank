package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountDao {
    private final DataWrapperDao dataWrapperDao;

    @Autowired
    public AccountDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    public Account getAccount() {
        Account account = dataWrapperDao.loadJsonFile().getObject("account", Account.class);
        log.info("Request account data");
        log.info("Get Account data: {}", account);
        return account;
    }

    public JSONObject getAccountJson() {
        JSONObject jsonObject = dataWrapperDao.loadJsonFile().getJSONObject("account");
        log.info("Request account data in JSON object");
        log.info("Get Account data in JSON object: {}", jsonObject);
        return jsonObject;
    }
}
