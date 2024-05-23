package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskList {
    @JSONField(name = "taskId")
    private long taskId;
    @JSONField(name = "taskName")
    private String taskName;
    @JSONField(name = "taskDescription")
    private String taskDescription;
    @JSONField(name = "taskStatus")
    private boolean taskStatus;
}
