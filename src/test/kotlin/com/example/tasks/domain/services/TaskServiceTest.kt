package com.example.tasks.domain.services

import com.example.tasks.domain.dto.TaskDto
import com.example.tasks.domain.models.Task
import com.example.tasks.domain.repo.TaskRepository
import io.github.oshai.kotlinlogging.KLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class TaskServiceTest(
    @Autowired
    private val taskService: TaskService,
    @Autowired
    private val taskRepository: TaskRepository,
    @Autowired
    private val logger: KLogger
) {
    private val taskDto = TaskDto(
        name = "i want pass the test",
        description = "description",
        creationDate = LocalDateTime.now().toString()
    )

    private val tasks = listOf(
        TaskDto(
            name = "first",
            creationDate = LocalDateTime.now().toString()
        ),
        TaskDto(
            name = "second",
            creationDate = LocalDateTime.now().toString()
        ),
        TaskDto(
            name = "third",
            creationDate = LocalDateTime.now().toString()
        )
    )

    @BeforeEach
//    @AfterEach
    fun setUp() = taskService.dropTable()

    @Test
    @Transactional
    fun findAll() {
        val all = taskService.findAll()

        logger.warn { all }
    }

    @Test
    @Transactional
    fun createTask() {
        val task = Task(
            name = "creating task test",
            creationDate = Task.now()
        )
        val task2 = taskDto.toModel()

        logger.warn { "TASK1 = $task" }
        logger.warn { "TASKDTO = $taskDto" }
        logger.warn { "TASK2 = $task2" }

//        val saved = taskRepository.save(task)
        val saved = taskService.createTask(taskDto)

        logger.warn { "entity is saved $saved" }

        assertThat(saved).isNotNull()
    }

    @Test
    @Transactional
    fun notCreateSameTask() {
        taskService.createTask(tasks[0])
        val task = TaskDto(name = tasks[0].name)

        val exception = assertThrows<DbActionExecutionException> { taskService.createTask(tasks[0]) }

        logger.warn { exception.message }
    }

    @Test
    @Transactional
    fun notCreateSameNameTask() {
        taskService.createTask(tasks[0])
        val task = TaskDto(name = tasks[0].name)

        val exception = assertThrows<DbActionExecutionException> { taskService.createTask(task) }

        logger.warn { exception.message }
    }

    @Test
    @Transactional
    fun updateTask() {

    }

    @Test
    @Transactional
    fun testUpdateTask() {
    }

    @Test
    @Transactional
    fun updateTaskName() {
    }

    @Test
    @Transactional
    fun updateTaskDescription() {
    }

    @Test
    @Transactional
    fun updateTaskDone() {
    }
}