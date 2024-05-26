package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The AdminDao class is a data access object (DAO) that handles administrator-related data operations
 * It extends the AbstractDao class and uses the DataWrapperDao class to load, set, and get administrator properties
 */
@Repository
@Slf4j
public class AdminDao extends AbstractDao {

    /**
     * An instance of DataWrapperDao for data encapsulation and unencapsulation operations
     */
    private final DataWrapperDao dataWrapperDao;

    /**
     * Constructor that receives an instance of DataWrapperDao for subsequent data operations
     *
     * @param dataWrapperDao Instance of DataWrapperDao
     */
    @Autowired
    public AdminDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    /**
     * Loads the administrator data and encapsulates it as a JSONObject object
     *
     * @return The JSONObject object that contains the administrator data
     */
    @Override
    public JSONObject load() {
        log.info("Request admin data in JSON format");
        JSONObject admin = dataWrapperDao.load().getJSONObject("admin");
        log.debug("Get admin data {}", admin);
        return admin;
    }

    /**
     * Sets the value of a property for the administrator
     *
     * @param attrname The name of the attribute
     * @param value    The value of the attribute
     */
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

    /**
     * Sets the password for the admin account
     *
     * @param password The new password to set
     * @return The updated Admin object with the new password set
     */
    private Admin setAdminPassword(String password) {
        Admin admin = this.load().toJavaObject(Admin.class);
        admin.setAdminPassword(password);
        return admin;
    }

    /**
     * Sets the first login status for the admin account
     *
     * @param firstLogin The first login status to set
     * @return The updated Admin object with the new first login status set
     */
    private Admin setFirstLogin(boolean firstLogin) {
        Admin admin = this.load().toJavaObject(Admin.class);
        admin.setFirstLogin(firstLogin);
        return admin;
    }

    /**
     * Retrieves the value of an administrator's property based on the property name
     *
     * @param attrname The name of the attribute
     * @return The value of the specified attribute
     */
    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "adminPassword" -> this.getAdminPassword();
            case "firstLogin" -> this.getFirstLogin();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    /**
     * Retrieves the password for the admin account
     *
     * @return The password for the admin account
     */
    private String getAdminPassword() {
        log.info("Request adminPassword");
        String adminPassword = this.load().getString("adminPassword");
        log.debug("Get adminPassword {}", adminPassword);
        return adminPassword;
    }

    /**
     * Retrieves the first login status for the admin account
     *
     * @return The first login status for the admin account
     */
    private boolean getFirstLogin() {
        log.info("Request firstLogin");
        boolean firstLogin = this.load().getBoolean("firstLogin");
        log.debug("Get firstLogin {}", firstLogin);
        return firstLogin;
    }

    /**
     * Retrieves all attributes of the administrator and returns them as a list
     *
     * @return A list containing all attributes of the admin
     */
    @Override
    public List<Object> getAllAttributes() {
        log.info("Request all attributes of admin");
        List<Object> objectList = List.of(getAttribute("adminPassword"), getAttribute("firstLogin"));
        log.debug("Get all attributes of admin {}", objectList);
        return objectList;
    }
}
