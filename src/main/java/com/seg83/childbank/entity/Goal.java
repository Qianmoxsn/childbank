package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * Goal class represents a goal.
 */
@Data
public class Goal {

    /**
     * Goal amount.
     */
    @JSONField(name = "goalAmount")
    private double goalAmount;
}
