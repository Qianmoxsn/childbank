package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.seg83.childbank.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final Resource jsonTemplatePath;
    private final Path jsonFilePath;

    /**
     * Constructs a new DataWrapperDao with the specified JSON file path
     *
     * @param jsonTemplatePath the path to the JSON template file
     * @param jsonDataPath     the path to the JSON data file
     */
    public DataWrapperDao(@Value("${json.template-path}") String jsonTemplatePath, @Value("${json.data-path}") String jsonDataPath) {
        Resource tempResource = null;
        Path tempPath = null;
        try {
            tempResource = new ClassPathResource(jsonTemplatePath);
        } catch (Exception e) {
            log.error("Failed to load internal JSON template:{}", jsonTemplatePath);
            throw new RuntimeException("Failed to load internal JSON template: " + e);
        }
        try {
            tempPath = Paths.get(System.getProperty("user.dir"), jsonDataPath);
        } catch (InvalidPathException e) {
            log.error("Invalid path for external JSON file: {}", jsonDataPath);
            throw new RuntimeException("Invalid path for external JSON file: " + e);
        }

        this.jsonTemplatePath = tempResource;
        this.jsonFilePath = tempPath;
    }

    /**
     * Loads the JSON file and returns its content as a JSONObject
     *
     * @return the JSONObject representing the content of the JSON file
     */
    public JSONObject loadJsonFile() {
        try {
            // Read from external when exist and non-empty
            if (Files.exists(jsonFilePath) && Files.size(jsonFilePath) > 0) {
                String json = new String(Files.readAllBytes(jsonFilePath), StandardCharsets.UTF_8);
                return JSONObject.parseObject(json);
            } else if (jsonTemplatePath.exists()) {
                // Read from internal resource
                try (InputStream is = jsonTemplatePath.getInputStream()) {
                    String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    return JSONObject.parseObject(json);
                }
            }
        } catch (IOException e) {
            log.error("Failed to load JSON file", e);
            throw new RuntimeException("Failed to load JSON file", e);
        }
        log.error("JSON file not found, both external and internal");
        throw new RuntimeException("JSON file not found, both external and internal");
    }

    /**
     * Saves the DataWrapper object to the JSON file
     *
     * @param dataWrapper the DataWrapper object to save
     */
    public void saveJsonFile(DataWrapper dataWrapper) {
        try {
            String json = JSON.toJSONString(dataWrapper, JSONWriter.Feature.PrettyFormat);
            Files.write(jsonFilePath, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Failed to save JSON file", e);
            throw new RuntimeException("Failed to save JSON file", e);
        }
    }

    /**
     * Loads the data from the JSON file and returns it as a JSONObject
     *
     * @return the JSONObject representing the data
     */
    @Override
    public JSONObject load() {
        return this.loadJsonFile();
    }

    /**
     * Sets the specified attribute of the DataWrapper object and saves it to the JSON file
     *
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
            case "goal" -> modifiedDataWrapper = this.setGoal((Goal) value);
            default -> throw new RuntimeException("Invalid attribute name");
        }
        this.saveJsonFile(modifiedDataWrapper);
    }

    /**
     * Sets the account attribute of the DataWrapper object
     *
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
     *
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
     * Sets the goal attribute of the DataWrapper object
     *
     * @param value the new Goal object to set
     * @return the modified DataWrapper object
     */
    private DataWrapper setGoal(Goal value) {
        DataWrapper dataWrapper = this.load().toJavaObject(DataWrapper.class);
        dataWrapper.setGoal(value);
        return dataWrapper;
    }

    /**
     * Retrieves the specified attribute of the DataWrapper object
     *
     * @param attrname the name of the attribute to retrieve
     * @return the value of the specified attribute
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
     *
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
     *
     * @return the Account object representing the account attribute
     */
    private Account getAccount() {
        log.info("Request account data in JSON format");
        Account account = this.load().getJSONObject("account").toJavaObject(Account.class);
        log.debug("Get account data {}", account);
        return account;
    }

    /**
     * Retrieves all attributes of the DataWrapper object and returns them as a list
     *
     * @return a list containing all attributes of the DataWrapper object
     */
    @Override
    public List<Object> getAllAttributes() {
        return List.of(getAttribute("admin"), getAttribute("account"));
    }
}
