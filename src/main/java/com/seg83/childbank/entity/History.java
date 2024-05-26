package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * History class represents a history of actions.
 */
@Data
public class History {

    /**
     * List of HistoryActions objects associated with the history.
     */
    @JSONField(name = "historyActions")
    private List<HistoryActions> historyActions;
}

