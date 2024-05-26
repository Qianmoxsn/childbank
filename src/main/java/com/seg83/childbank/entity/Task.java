package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Task class represents a task.
 */
@Data
public class Task {

    /**
     * List of TaskList objects associated with the task.
     */
    @JSONField(name = "taskList")
    private List<TaskList> taskList;
}

