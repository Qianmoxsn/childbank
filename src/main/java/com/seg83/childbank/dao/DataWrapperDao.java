package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
@Slf4j
public class DataWrapperDao {

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
            String json = JSON.toJSONString(jsonData);
            Files.write(jsonFilePath, json.getBytes());
        } catch (IOException e) {
            log.error("Failed to save JSON file", e);
            throw new RuntimeException("Failed to save JSON file", e);
        }
    }
}
