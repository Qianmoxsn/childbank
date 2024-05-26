package com.seg83.childbank.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TaskList class represents a task list.
 */
@Data
@AllArgsConstructor
public class TaskList {

    /**
     * Task ID.
     */
    @JSONField(name = "taskId")
    private long taskId;

    /**
     * Task name.
     */
    @JSONField(name = "taskName")
    private String taskName;

    /**
     * Task description.
     */
    @JSONField(name = "taskDescription")
    private String taskDescription;

    /**
     * Task status.
     */
    @JSONField(name = "taskStatus")
    private boolean taskStatus;
}

