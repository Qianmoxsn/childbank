package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class AdminDao extends AbstractDao {
    private final DataWrapperDao dataWrapperDao;

    @Autowired
    public AdminDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    // TODO: 返回json
    // TODO: 打log

    @Override
    public JSONObject load() {
        log.info("Request admin data in JSON format");
        JSONObject admin = dataWrapperDao.load().getJSONObject("admin");
        log.debug("Get admin data {}", admin);
        return admin;
    }

    @Override
    public void save(JSONObject jsobj) {

    }

    @Override
    public void setAttribute(String attrname, Object value) {
        switch (attrname) {
            case "adminPassword" -> {
                log.info("Setting admin password to {}", value);
                this.setAdminPassword((String) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }

    }

    @Override
    public Object getAttribute(String attrname) {
        switch (attrname) {
            case "adminPassword" -> {
                log.info("Request adminPassword");
                String adminPassword = this.load().getString("adminPassword");
                log.debug("Get adminPassword {}", adminPassword);
                return adminPassword;
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
    }

    @Override
    public List<Object> getAllAttributes() {
        log.info("Request all attributes of admin");
        List<Object> objectList = List.of(getAttribute("adminPassword"));
        log.debug("Get all attributes of admin {}", objectList);
        return objectList;
    }

    private JSONObject setAdminPassword(String password) {
        JSONObject adminJson = this.load();
        adminJson.put("adminPassword", password);
        return adminJson;
    }

}
