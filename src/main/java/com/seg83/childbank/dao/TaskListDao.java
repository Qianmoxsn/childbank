package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;
import com.seg83.childbank.entity.TaskList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TaskListDao extends AbstractArrayDao {
    long ElementCount;
    private TaskDao taskDao;

    @Autowired
    public TaskListDao(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.getElementCount();
    }

    @Override
    JSONArray load() {
        log.info("Request task list data in JSON format");
        JSONArray taskList = taskDao.load().getJSONArray("taskList");
        log.debug("Get task list data {}", taskList);
        return taskList;
    }

    @Override
    void getElementCount() {
        log.info("Request tasks count");
        this.ElementCount = this.load().size();
        log.debug("Get history actions count {}", this.ElementCount);
    }

    @Override
    Object getElementById(long Id) {
        log.info("Request task by id {}", Id);
        List<TaskList> taskLists = this.load().toList(TaskList.class);
        for (TaskList taskList : taskLists) {
            if (taskList.getTaskId() == Id) {
                log.debug("Get task {}", taskList);
                return taskList;
            }
        }
        throw new RuntimeException("Invalid task id");
    }

    @Override
    @Deprecated
    public void setAttribute(String attrname, Object value, long Id) {
        switch (attrname) {
            case "taskStatus" -> {
                log.info("Set task status to {} for task id {}", value, Id);
                this.setTaskStatue(Id, (boolean) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
    }

    private void setTaskStatue(long Id, boolean value) {
        List<TaskList> taskLists = this.load().toList(TaskList.class);
        for (TaskList taskList : taskLists) {
            if (taskList.getTaskId() == Id) {
                taskList.setTaskStatus(value);
                log.debug("Set task status to {}", value);
                taskDao.setAttribute("taskList", taskLists);
                return;
            }
        }
        throw new RuntimeException("Invalid task id");
    }

    public void createTask(String taskName, String taskDescription) {
        log.info("Create task with name {} and description {}", taskName, taskDescription);
        TaskList newtask = new TaskList(this.ElementCount + 1, taskName, taskDescription, false);
        this.ElementCount++;
        log.debug("New task {}", newtask);

        List<TaskList> taskList = this.load().toList(TaskList.class);
        taskList.add(newtask);
        log.debug("Task list after adding new task {}", taskList);

        taskDao.setAttribute("taskList", taskList);
    }

    @Override
    public Object getAttribute(String attrname, long Id) {
        switch (attrname) {
            case "taskName" -> {
                log.info("Request task name for task id {}", Id);
                return this.getTaskName(Id);
            }
            case "taskDescription" -> {
                log.info("Request task description for task id {}", Id);
                return this.getTaskDescription(Id);
            }
            case "taskStatus" -> {
                log.info("Request task status for task id {}", Id);
                return this.getTaskStatus(Id);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
    }

    private String getTaskName(long Id) {
        return ((TaskList) this.getElementById(Id)).getTaskName();
    }

    private String getTaskDescription(long Id) {
        return ((TaskList) this.getElementById(Id)).getTaskDescription();
    }

    private boolean getTaskStatus(long Id) {
        return ((TaskList) this.getElementById(Id)).isTaskStatus();
    }

    @Override
    List<Object> getAllAttributes() {
        return List.of();
    }
}
