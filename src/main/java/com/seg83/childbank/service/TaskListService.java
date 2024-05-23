package com.seg83.childbank.service;

import com.seg83.childbank.dao.TaskListDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskListService {
    @Autowired
    private TaskListDao taskListDao;

    public void addNewTask(String taskName, String taskDescription) {
        log.info("Adding new task with name {} and description {}", taskName, taskDescription);
        taskListDao.createTask(taskName, taskDescription);
    }

    public void finishTask(long taskId) {
        log.info("Finishing task with id {}", taskId);
        taskListDao.setAttribute("taskStatus", true, taskId);
    }
}
