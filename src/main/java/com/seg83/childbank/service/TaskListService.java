package com.seg83.childbank.service;

import com.seg83.childbank.dao.TaskDao;
import com.seg83.childbank.dao.TaskListDao;
import com.seg83.childbank.entity.TaskList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TaskListService provides methods for managing task list and related operations.
 */
@Service
@Slf4j
public class TaskListService {

    /**
     * TaskListDao instance for handling task list data access.
     */
    @Autowired
    private TaskListDao taskListDao;

    /**
     * TaskDao instance for handling task data access.
     */
    @Autowired
    private TaskDao taskDao;

    /**
     * Adds a new task to the task list.
     *
     * @param taskName     the name of the task
     * @param taskDescription the description of the task
     */
    public void addNewTask(String taskName, String taskDescription) {
        log.info("Adding new task with name {} and description {}", taskName, taskDescription);
        taskListDao.createTask(taskName, taskDescription);
    }

    /**
     * Marks a task as finished.
     *
     * @param taskId the ID of the task to finish
     */
    public void finishTask(long taskId) {
        log.info("Finishing task with id {}", taskId);
        taskListDao.setAttribute("taskStatus", true, taskId);
    }

    /**
     * Generates a list of tasks for display in a JTable.
     *
     * @return a 2D array of objects representing the task list records
     */
    public Object[][] generateTaskList() {
        log.info("Generating Task List...");
        List<TaskList> taskList = (List<TaskList>) taskDao.getAttribute("taskList");

        // For swing JTable
        Object[][] data = new Object[taskList.size()][4];
        for (int i = 0; i < taskList.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = taskListDao.getAttribute("taskName", i + 1);
            data[i][2] = taskListDao.getAttribute("taskDescription", i + 1);
            data[i][3] = taskListDao.getAttribute("taskStatus", i + 1);
        }
        return data;
    }
}
