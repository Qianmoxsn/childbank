package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositAccountBill {
    @JSONField(name = "depositAccountBillId")
    private long depositAccountBillId;

    @JSONField(name = "depositAccountBillAmount")
    private double depositAccountBillAmount;

    @JSONField(name = "depositAccountBillRate")
    private double depositAccountBillRate;

    @JSONField(name = "depositAccountBillExpireDate")
    private String depositAccountBillExpireDate;
}
