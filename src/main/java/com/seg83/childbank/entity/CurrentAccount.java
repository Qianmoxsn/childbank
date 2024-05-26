package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * CurrentAccount class represents a current account.
 */
@Data
public class CurrentAccount {

    /**
     * Amount in the current account.
     */
    @JSONField(name = "currentAccountAmount")
    private double currentAccountAmount;

    /**
     * Interest rate of the current account.
     */
    @JSONField(name = "currentAccountRate")
    private double currentAccountRate;

    /**
     * Last interest date of the current account.
     */
    @JSONField(name = "lastInterestDate")
    private String lastInterestDate;
}
