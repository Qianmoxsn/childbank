package com.seg83.childbank.entity;


import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Admin class represents an admin user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    /**
     * Admin password.
     */
    @JSONField(name = "adminPassword")
    private String adminPassword;

    /**
     * Indicates if it is the first login for the admin user.
     */
    @JSONField(name = "firstLogin")
    private boolean firstLogin;
}
