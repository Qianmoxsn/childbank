package com.seg83.childbank.entity;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @JSONField(name = "adminPassword")
    private String adminPassword;

    @JSONField(name = "firstLogin")
    private boolean firstLogin;

}
