package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class DepositAccountBill {
    @JSONField(name = "depositAccountBillId")
    private String depositAccountBillId;

    @JSONField(name = "depositAccountBillAmount")
    private String depositAccountBillAmount;

    @JSONField(name = "depositAccountBillRate")
    private String depositAccountBillRate;

    @JSONField(name = "depositAccountBillExpireDate")
    private String depositAccountBillExpireDate;
}
