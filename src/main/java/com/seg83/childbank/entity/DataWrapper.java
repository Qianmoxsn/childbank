package com.seg83.childbank.entity;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class DataWrapper {
    @JSONField(name = "admin")
    private Admin admin;

    @JSONField(name = "account")
    private Account account;

    @JSONField(name = "history")
    private History history;
}
