package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Account class represents an account with a deposit account and a current account.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    /**
     * Account password.
     */
    @JSONField(name = "accountPassword")
    private String accountPassword;

    /**
     * Deposit account associated with the account.
     */
    @JSONField(name = "depositAccount")
    private DepositAccount depositAccount;

    /**
     * Current account associated with the account.
     */
    @JSONField(name = "currentAccount")
    private CurrentAccount currentAccount;
}

