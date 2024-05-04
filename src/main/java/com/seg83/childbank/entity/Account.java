package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @JSONField(name = "accountPassword")
    private String accountPassword;

    @JSONField(name = "depositAccount")
    private DepositAccount depositAccount;

    @JSONField(name = "currentAccount")
    private CurrentAccount currentAccount;
}
