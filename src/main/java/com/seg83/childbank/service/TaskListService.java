package com.seg83.childbank.service;

import com.seg83.childbank.dao.TaskDao;
import com.seg83.childbank.dao.TaskListDao;
import com.seg83.childbank.entity.TaskList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskListService {
    @Autowired
    private TaskListDao taskListDao;
    @Autowired
    private TaskDao taskDao;

    public void addNewTask(String taskName, String taskDescription) {
        log.info("Adding new task with name {} and description {}", taskName, taskDescription);
        taskListDao.createTask(taskName, taskDescription);
    }

    public void finishTask(long taskId) {
        log.info("Finishing task with id {}", taskId);
        taskListDao.setAttribute("taskStatus", true, taskId);
    }

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
