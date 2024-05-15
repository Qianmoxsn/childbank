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
     * An example of DataWrapperDao for data encapsulation and unencapsulation operations
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
     * Load the administrator data and encapsulate it as a JSONObject object
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
     * @param attrname the name of the attribute
     * @param value    the value of the attribute
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
     * Gets the administrator's property value based on the property name.
     *
     * @param password the administrator's password
     * @return attribute value
     */
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

    /**
     * Private method for setting the administrator's password
     *
     * @return Specifies the administrator object after the password is changed
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
     * Private method to get the administrator's password
     *
     * @return Administrator password
     */
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

    /**
     * Gets all the administrator's property values and returns them as a list
     *
     * @return contains a list of all property values for the administrator
     */
    @Override
    public List<Object> getAllAttributes() {
        log.info("Request all attributes of admin");
        List<Object> objectList = List.of(getAttribute("adminPassword"));
        log.debug("Get all attributes of admin {}", objectList);
        return objectList;
    }
}