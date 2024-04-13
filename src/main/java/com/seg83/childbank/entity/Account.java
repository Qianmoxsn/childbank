package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Account {


    @JSONField(name = "depositAccount")
    private DepositAccount depositAccount;

    @JSONField(name = "currentAccount")
    private CurrentAccount currentAccount;
}
