package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.seg83.childbank.entity.Account;
import com.seg83.childbank.entity.Admin;
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

    public void saveJsonFile(JSONObject jsonData) {
        try {
            String json = JSON.toJSONString(jsonData, JSONWriter.Feature.PrettyFormat);
            Files.write(jsonFilePath, json.getBytes());
        } catch (IOException e) {
            log.error("Failed to save JSON file", e);
            throw new RuntimeException("Failed to save JSON file", e);
        }
    }

    public void saveJsonFile(JSONObject childJsonObject, String childName) {
        try {
            JSONObject jsonData = this.loadJsonFile();
            jsonData.put(childName, childJsonObject);
            String json = JSON.toJSONString(jsonData, JSONWriter.Feature.PrettyFormat);
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
    public void save(JSONObject jsobj) {

    }

    @Override
    public void setAttribute(String attrname, Object value) {
        // forbidden to set attributes of the data wrapper
    }

    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "admin" -> this.load().getJSONObject("admin").toJavaObject(Admin.class);
            case "account" -> this.load().getJSONObject("account").toJavaObject(Account.class);
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    @Override
    public List<Object> getAllAttributes() {
        return List.of(getAttribute("admin"), getAttribute("account"));
    }
}
