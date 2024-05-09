package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.seg83.childbank.entity.Account;
import com.seg83.childbank.entity.Admin;
import com.seg83.childbank.entity.DataWrapper;
import com.seg83.childbank.entity.History;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * DataWrapperDao class that handles the data operations related to DataWrapper entity
 */
@Repository
@Slf4j
public class DataWrapperDao extends AbstractDao {
    /**
     * The file path where the JSON data is stored
     */

    private final Path jsonFilePath;

    /**
     * Constructs a new DataWrapperDao with the specified JSON file path
     * @param jsonFilePath the path to the JSON file
     */

    public DataWrapperDao(@Value("${json.template-path}") Path jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    /**
     * Loads the JSON file and returns its content as a JSONObject
     * @return the JSONObject representing the content of the JSON file
     */


    public JSONObject loadJsonFile() {
        try {
            String json = new String(Files.readAllBytes(jsonFilePath));
            return JSON.parseObject(json);
        } catch (IOException e) {
            log.error("Failed to load JSON file", e);
            throw new RuntimeException("Failed to load JSON file", e);
        }
    }

    /**
     * the JSONObject representing the content of the JSON file
     * @param dataWrapper the DataWrapper object to save
     */

    public void saveJsonFile(DataWrapper dataWrapper) {
        try {
            String json = JSON.toJSONString(dataWrapper, JSONWriter.Feature.PrettyFormat);
            Files.write(jsonFilePath, json.getBytes());
        } catch (IOException e) {
            log.error("Failed to save JSON file", e);
            throw new RuntimeException("Failed to save JSON file", e);
        }
    }

    /**
     * @return
     */

    @Override
    public JSONObject load() {
        return this.loadJsonFile();
    }

    /**
     * Sets the specified attribute of the DataWrapper object and saves it to the JSON file
     * @param attrname the name of the attribute to set
     * @param value    the value of the attribute
     */

    @Override
    public void setAttribute(String attrname, Object value) {
        DataWrapper modifiedDataWrapper;
        switch (attrname) {
            case "admin" -> modifiedDataWrapper = this.setAdmin((Admin) value);
            case "account" -> modifiedDataWrapper = this.setAccount((Account) value);
            case "history" -> modifiedDataWrapper = this.setHistory((History) value);
            default -> throw new RuntimeException("Invalid attribute name");
        }
        this.saveJsonFile(modifiedDataWrapper);
    }

    /**
     * Sets the account attribute of the DataWrapper object
     * @param value the new Account object to set
     * @return the modified DataWrapper object
     */

    private DataWrapper setAccount(Account value) {
        DataWrapper dataWrapper = this.load().toJavaObject(DataWrapper.class);
        dataWrapper.setAccount(value);
        return dataWrapper;
    }

    /**
     * Sets the admin attribute of the DataWrapper object
     * @param value the new Admin object to set
     * @return the modified DataWrapper object
     */

    private DataWrapper setAdmin(Admin value) {
        DataWrapper dataWrapper = this.load().toJavaObject(DataWrapper.class);
        dataWrapper.setAdmin(value);
        return dataWrapper;
    }

    /**
     * Sets the history attribute of the DataWrapper object
     *
     * @param value the new History object to set
     * @return the modified DataWrapper object
     */

    private DataWrapper setHistory(History value) {
        DataWrapper dataWrapper = this.load().toJavaObject(DataWrapper.class);
        dataWrapper.setHistory(value);
        return dataWrapper;
    }

    /**
     * Sets the history for the DataWrapper object
     * @param attrname the name of the attribute
     * @return The updated DataWrapper object with the new history value
     */

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "admin" -> this.getAdmin();
            case "account" -> this.getAccount();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    /**
     * Retrieves the admin attribute from the DataWrapper object
     * @return the Admin object representing the admin attribute
     */

    private Admin getAdmin() {
        log.info("Request admin data in JSON format");
        Admin admin = this.load().getJSONObject("admin").toJavaObject(Admin.class);
        log.debug("Get admin data {}", admin);
        return admin;
    }

    /**
     * Retrieves the account attribute from the DataWrapper object
     * @return the Account object representing the account attribute
     */

    private Account getAccount() {
        log.info("Request account data in JSON format");
        Account account = this.load().getJSONObject("account").toJavaObject(Account.class);
        log.debug("Get account data {}", account);
        return account;
    }

    /**
     * Retrieves the account data and converts it to an Account object
     * @return An Account object representing the account data loaded from storage
     */

    @Override
    public List<Object> getAllAttributes() {
        return List.of(getAttribute("admin"), getAttribute("account"));
    }
}