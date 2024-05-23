package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Task {
    @JSONField(name = "taskList")
    private List<TaskList> taskList;
}
