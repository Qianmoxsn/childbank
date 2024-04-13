package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class CurrentAccount {


    @JSONField(name = "currentAccountAmount")
    private String currentAccountAmount;

    @JSONField(name = "currentAccountRate")
    private String currentAccountRate;
}
