package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONObject;
import com.seg83.childbank.entity.Task;
import com.seg83.childbank.entity.TaskList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TaskDao extends AbstractDao {
    @Autowired
    private DataWrapperDao dataWrapperDao;

    @Override
    JSONObject load() {
        log.info("Request task data in JSON format");
        JSONObject task = dataWrapperDao.load().getJSONObject("task");
        log.debug("Get task data {}", task);
        return task;
    }

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

    private Task setTaskList(List<TaskList> value) {
        Task task = this.load().toJavaObject(Task.class);
        task.setTaskList(value);
        return task;
    }

    @Override
    Object getAttribute(String attrname) {
        return null;
    }

    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}
