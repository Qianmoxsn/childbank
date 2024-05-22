package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Goal {
    @JSONField(name = "goalAmount")
    private double goalAmount;
}
