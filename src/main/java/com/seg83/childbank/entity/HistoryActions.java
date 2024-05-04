package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class HistoryActions {

    @JSONField(name = "historyId")
    private long historyId;

    @JSONField(name = "historyDateTime")
    private String historyDateTime;

    @JSONField(name = "historyAmount")
    private double historyAmount;

    @JSONField(name = "historyType")
    private String historyType;


}
