package com.seg83.childbank.dao;

import com.alibaba.fastjson2.JSONArray;
import com.seg83.childbank.entity.TaskList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data Access Object (DAO) for managing task list data.
 */
@Component
@Slf4j
public class TaskListDao extends AbstractArrayDao {
    long ElementCount;

    private TaskDao taskDao;

    /**
     * Constructor for TaskListDao.
     *
     * @param taskDao TaskDao instance for managing task data.
     */
    @Autowired
    public TaskListDao(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.getElementCount();
    }

    /**
     * Load task list data in JSON format.
     *
     * @return JSONArray containing task list data.
     */
    @Override
     JSONArray load() {
        log.info("Request task list data in JSON format");
        JSONArray taskList = taskDao.load().getJSONArray("taskList");
        log.debug("Get task list data {}", taskList);
        return taskList;
    }

    /**
     * Get the number of tasks in the task list.
     *
     * @return Long containing the number of tasks in the task list.
     */
    @Override
    void getElementCount() {
        log.info("Request tasks count");
        this.ElementCount = this.load().size();
        log.debug("Get history actions count {}", this.ElementCount);
        return this.ElementCount;
    }

    /**
     * Get a task by its ID.
     *
     * @param Id ID of the task to get.
     * @return TaskList object containing the task data.
     */
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

    /**
     * Set the task status for a specific task.
     *
     * @param Id       ID of the task to set the status for.
     * @param value  Value to set for the task status.
     */
    @Deprecated
    @Override
    public void setAttribute(String attrname, Object value, long Id) {
        switch (attrname) {
            case "taskStatus" -> {
                log.info("Set task status to {} for task id {}", value, Id);
                this.setTaskStatue(Id, (boolean) value);
            }
            default -> throw new RuntimeException("Invalid attribute name");
        }
    }

    /**
     * Set the task status for a specific task.
     *
     * @param Id       ID of the task to set the status for.
     * @param value  Value to set for the task status.
     */
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

    /**
     * Create a new task.
     *
     * @param taskName  Name of the task to create.
     * @param taskDescription Description of the task to create.
     */
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

    /**
     * Get an attribute of a task.
     *
     * @param attrname   Name of the attribute to get.
     * @param Id       ID of the task to get the attribute for.
     * @return Object containing the attribute value.
     */
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

    /**
     * Get the task name for a specific task.
     *
     * @param Id ID of the task to get the name for.
     * @return String containing the task name.
     */
    private String getTaskName(long Id) {
        return ((TaskList) this.getElementById(Id)).getTaskName();
    }

    /**
     * Get the task description for a specific task.
     *
     * @param Id ID of the task to get the description for.
     * @return String containing the task description.
     */
    private String getTaskDescription(long Id) {
        return ((TaskList) this.getElementById(Id)).getTaskDescription();
    }

    /**
     * Get the task status for a specific task.
     *
     * @param Id ID of the task to get the status for.
     * @return Boolean containing the task status.
     */
    private boolean getTaskStatus(long Id) {
        return ((TaskList) this.getElementById(Id)).isTaskStatus();
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
