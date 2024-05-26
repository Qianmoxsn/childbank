package com.seg83.childbank.entity;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * DataWrapper class is a container for various data objects.
 */
@Data
public class DataWrapper {

    /**
     * Admin object.
     */
    @JSONField(name = "admin")
    private Admin admin;

    /**
     * Account object.
     */
    @JSONField(name = "account")
    private Account account;

    /**
     * History object.
     */
    @JSONField(name = "history")
    private History history;

    /**
     * Goal object.
     */
    @JSONField(name = "goal")
    private Goal goal;

    /**
     * Task object.
     */
    @JSONField(name = "task")
    private Task task;
}

