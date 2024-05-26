package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DepositAccountBills class represents a deposit account bill.
 */
@Data
@AllArgsConstructor
public class DepositAccountBills {

    /**
     * Deposit account bill ID.
     */
    @JSONField(name = "depositAccountBillId")
    private long depositAccountBillId;

    /**
     * Deposit account bill amount.
     */
    @JSONField(name = "depositAccountBillAmount")
    private double depositAccountBillAmount;

    /**
     * Deposit account bill interest rate.
     */
    @JSONField(name = "depositAccountBillRate")
    private double depositAccountBillRate;

    /**
     * Deposit account bill effective date.
     */
    @JSONField(name = "depositAccountBillEffectiveDate")
    private String depositAccountBillEffectiveDate;

    /**
     * Deposit account bill expire date.
     */
    @JSONField(name = "depositAccountBillExpireDate")
    private String depositAccountBillExpireDate;
}

