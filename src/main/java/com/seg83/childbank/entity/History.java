package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class History {
    @JSONField(name = "historyActions")
    private List<HistoryActions> historyActions;
}
