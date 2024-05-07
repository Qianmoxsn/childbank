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

@Repository
@Slf4j
public class DataWrapperDao extends AbstractDao {

    private final Path jsonFilePath;

    public DataWrapperDao(@Value("${json.template-path}") Path jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }


    public JSONObject loadJsonFile() {
        try {
            String json = new String(Files.readAllBytes(jsonFilePath));
            return JSON.parseObject(json);
        } catch (IOException e) {
            log.error("Failed to load JSON file", e);
            throw new RuntimeException("Failed to load JSON file", e);
        }
    }

    public void saveJsonFile(DataWrapper dataWrapper) {
        try {
            String json = JSON.toJSONString(dataWrapper, JSONWriter.Feature.PrettyFormat);
            Files.write(jsonFilePath, json.getBytes());
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