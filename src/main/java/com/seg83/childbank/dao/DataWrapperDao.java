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

@Repository
@Slf4j
public class DataWrapperDao extends AbstractDao {

    private final Resource jsonTemplatePath;
    private final Path jsonFilePath;

    public DataWrapperDao(@Value("${json.template-path}") String jsonTemplatePath,
                          @Value("${json.data-path}") String jsonDataPath) {
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


    public JSONObject loadJsonFile() {
        try {
            // Read from external when exist and non-empty
            if (Files.exists(jsonFilePath) && Files.size(jsonFilePath) > 0){
                String json = new String(Files.readAllBytes(jsonFilePath), StandardCharsets.UTF_8);
                return JSONObject.parseObject(json);
            } else if (jsonTemplatePath.exists()) {
                // 从内部资源读取
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

    public void saveJsonFile(DataWrapper dataWrapper) {
        try {
            String json = JSON.toJSONString(dataWrapper, JSONWriter.Feature.PrettyFormat);
            Files.write(jsonFilePath, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Failed to save JSON file", e);
            throw new RuntimeException("Failed to save JSON file", e);
        }
    }

    @Override
    public JSONObject load() {
        return this.loadJsonFile();
    }

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

    private DataWrapper setAccount(Account value) {
        DataWrapper dataWrapper = this.load().toJavaObject(DataWrapper.class);
        dataWrapper.setAccount(value);
        return dataWrapper;
    }

    private DataWrapper setAdmin(Admin value) {
        DataWrapper dataWrapper = this.load().toJavaObject(DataWrapper.class);
        dataWrapper.setAdmin(value);
        return dataWrapper;
    }

    private DataWrapper setHistory(History value) {
        DataWrapper dataWrapper = this.load().toJavaObject(DataWrapper.class);
        dataWrapper.setHistory(value);
        return dataWrapper;
    }

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "admin" -> this.getAdmin();
            case "account" -> this.getAccount();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    private Admin getAdmin() {
        log.info("Request admin data in JSON format");
        Admin admin = this.load().getJSONObject("admin").toJavaObject(Admin.class);
        log.debug("Get admin data {}", admin);
        return admin;
    }

    private Account getAccount() {
        log.info("Request account data in JSON format");
        Account account = this.load().getJSONObject("account").toJavaObject(Account.class);
        log.debug("Get account data {}", account);
        return account;
    }

    @Override
    public List<Object> getAllAttributes() {
        return List.of(getAttribute("admin"), getAttribute("account"));
    }
}