package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * HistoryActions class represents a history action.
 */
@Data
@AllArgsConstructor
public class HistoryActions {

    /**
     * History action ID.
     */
    @JSONField(name = "historyId")
    private long historyId;

    /**
     * History action date and time.
     */
    @JSONField(name = "historyDateTime")
    private String historyDateTime;

    /**
     * History action amount.
     */
    @JSONField(name = "historyAmount")
    private double historyAmount;

    /**
     * History action type.
     */
    @JSONField(name = "historyType")
    private String historyType;
}
