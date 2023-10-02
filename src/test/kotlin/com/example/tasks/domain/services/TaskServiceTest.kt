package com.example.tasks.domain.services

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TaskServiceTest(
    @Autowired
    private val taskService: TaskService
) {

    @BeforeEach
    fun setUp() = taskService.dropTable()

    @AfterEach
    fun tearDown() = taskService.dropTable()

    @Test
    fun createTask() {
    }

    @Test
    fun updateTask() {
    }

    @Test
    fun testUpdateTask() {
    }

    @Test
    fun updateTaskName() {
    }

    @Test
    fun updateTaskDescription() {
    }

    @Test
    fun updateTaskDone() {
    }
}