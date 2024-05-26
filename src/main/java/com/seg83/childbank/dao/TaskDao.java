package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Task;
import com.seg83.childbank.entity.TaskList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data Access Object (DAO) for managing task data.
 */
@Component
@Slf4j
public class TaskDao extends AbstractDao {
    @Autowired


    private DataWrapperDao dataWrapperDao;

    /**
     * Constructor for TaskDao.
     *
     * @param dataWrapperDao DataWrapperDao instance for managing data wrapper data.
     */
    @Autowired
    public TaskDao(DataWrapperDao dataWrapperDao) {
        this.dataWrapperDao = dataWrapperDao;
    }

    /**
     * Load task data in JSON format.
     *
     * @return JSONObject containing task data.
     */
    @Override
    JSONObject load() {
        log.info("Request task data in JSON format");
        JSONObject task = dataWrapperDao.load().getJSONObject("task");
        log.debug("Get task data {}", task);
        return task;
    }

    /**
     * Set an attribute of a task.
     *
     * @param attrname   Name of the attribute to set.
     * @param value     Value to set for the attribute.
     */
    @Override
    void setAttribute(String attrname, Object value) {
        Task modifiedTask;
        switch (attrname) {
            case "taskList" -> {
                log.info("Setting task list to {}", value);
                modifiedTask = this.setTaskList((List<TaskList>) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
        this.dataWrapperDao.setAttribute("task", modifiedTask);
    }

    /**
     * Set the task list.
     *
     * @param value  List of TaskList objects to set for the task.
     * @return Task object with the updated task list.
     */
    private Task setTaskList(List<TaskList> value) {
        Task task = this.load().toJavaObject(Task.class);
        task.setTaskList(value);
        return task;
    }

    /**
     * Get an attribute of a task.
     *
     * @param attrname   Name of the attribute to get.
     * @return Object containing the attribute value.
     */
    @Override
    public Object getAttribute(String attrname) {
        return switch (attrname) {
            case "taskList" -> this.getTaskList();
            default -> throw new RuntimeException("Invalid attribute name");
        };
    }

    /**
     * Get the task list.
     *
     * @return List of TaskList objects containing the task list.
     */
    private List<TaskList> getTaskList() {
        return this.load().toJavaObject(Task.class).getTaskList();
    }

    /**
     * Get all attributes of a task.
     *
     * @return List of Objects containing all attributes of a task.
     */
    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}
