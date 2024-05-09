package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Admin;
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

    @Override
    public JSONObject load() {
        log.info("Request admin data in JSON format");
        JSONObject admin = dataWrapperDao.load().getJSONObject("admin");
        log.debug("Get admin data {}", admin);
        return admin;
    }

    @Override
    public void setAttribute(String attrname, Object value) {
        Admin modifiedAdmin;
        switch (attrname) {
            case "adminPassword" -> {
                log.info("Setting admin password to {}", value);
                modifiedAdmin = this.setAdminPassword((String) value);
            }
            case "firstLogin" -> {
                log.info("Setting first login to {}", value);
                modifiedAdmin = this.setFirstLogin((boolean) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
        dataWrapperDao.setAttribute("admin", modifiedAdmin);
    }

    private Admin setAdminPassword(String password) {
        Admin admin = this.load().toJavaObject(Admin.class);
        admin.setAdminPassword(password);
        return admin;
    }

    private Admin setFirstLogin(boolean firstLogin) {
        Admin admin = this.load().toJavaObject(Admin.class);
        admin.setFirstLogin(firstLogin);
        return admin;
    }

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "adminPassword" -> this.getAdminPassword();
            case "firstLogin" -> this.getFirstLogin();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    private String getAdminPassword() {
        log.info("Request adminPassword");
        String adminPassword = this.load().getString("adminPassword");
        log.debug("Get adminPassword {}", adminPassword);
        return adminPassword;
    }

    private boolean getFirstLogin() {
        log.info("Request firstLogin");
        boolean firstLogin = this.load().getBoolean("firstLogin");
        log.debug("Get firstLogin {}", firstLogin);
        return firstLogin;
    }

    @Override
    public List<Object> getAllAttributes() {
        log.info("Request all attributes of admin");
        List<Object> objectList = List.of(getAttribute("adminPassword"));
        log.debug("Get all attributes of admin {}", objectList);
        return objectList;
    }
}