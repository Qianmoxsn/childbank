package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * DepositAccount class represents a deposit account.
 */
@Data
public class DepositAccount {

    /**
     * List of DepositAccountBills objects associated with the deposit account.
     */
    @JSONField(name = "depositAccountBills")
    private List<DepositAccountBills> depositAccountBills;
}
