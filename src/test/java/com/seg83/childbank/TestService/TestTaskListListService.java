package com.seg83.childbank.TestService;

import com.seg83.childbank.dao.TaskListDao;
import com.seg83.childbank.service.TaskListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestTaskListListService {
    @Autowired
    private TaskListService taskListService;
    @Autowired
    private TaskListDao taskListDao;

    @BeforeAll
    static void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    @AfterEach
    void restoreTestJson() {
        try {
            Files.deleteIfExists(Path.of("data.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Remove :: test data json\n");
    }

    @Test
    void addNewTask() {
        taskListService.addNewTask("testTask", "testDescription");
        assertEquals("testTask", taskListDao.getAttribute("taskName", 3));
        assertEquals("testDescription", taskListDao.getAttribute("taskDescription", 3));
        assertEquals(false, taskListDao.getAttribute("taskStatus", 3));
    }

    @Test
    void finishTask() {
        long id = 2;
        taskListService.finishTask(id);
        assertEquals(true, taskListDao.getAttribute("taskStatus", 2));
    }

    @Test
    void generateTaskList() {
        Object[][] data = taskListService.generateTaskList();
        assertNotNull(data);
        assertEquals(2, data.length);
        assertEquals(1, data[0][0]);
        assertEquals("task1", data[0][1]);
        assertEquals("task1 description", data[0][2]);
        assertTrue((boolean) data[0][3]);
        assertEquals(2, data[1][0]);
        assertEquals("task2", data[1][1]);
        assertEquals("task2 description", data[1][2]);
        assertFalse((boolean) data[1][3]);
    }
}