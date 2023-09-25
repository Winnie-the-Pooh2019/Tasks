package com.example.tasks.domain.services

import com.example.tasks.domain.repo.TaskRepository
import com.example.tasks.domain.dto.TaskDto
import com.example.tasks.domain.models.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import kotlin.NoSuchElementException

@Service
class TaskService(
    @Autowired
    private val taskRepository: TaskRepository
) {
    fun getAll() = taskRepository.findAll()

    fun getById(id: String): Task? {
        val taskO = taskRepository.findById(UUID.fromString(id))

        return if (taskO.isEmpty)
            null
        else
            taskO.get()
    }

    @Transactional
    fun getPaginatedTasks(page: Int, pageSize: Int): Page<Task> {
        val tasks = taskRepository.findPaginatedTasks(pageSize, (page - 1) * pageSize).get()
        val count = taskRepository.count()

        return Page(tasks, page, count.toInt())
    }

    @Throws(DbActionExecutionException::class)
    fun createTask(taskDto: TaskDto): Task = taskRepository.save(taskDto.toModel())

    @Transactional
    @Throws(NoSuchElementException::class, DbActionExecutionException::class)
    fun updateTask(taskDto: TaskDto): Task {
        if (taskDto.id == null || taskRepository.findById(UUID.fromString(taskDto.id)).isEmpty)
            throw NoSuchElementException("No id is provided or id is corrupted")

        return taskRepository.save(taskDto.toModel())
    }

    @Transactional
    @Throws(NoSuchElementException::class, DbActionExecutionException::class)
    fun updateTask(task: Task): Task {
        if (task.id == null || taskRepository.findById(task.id).isEmpty)
            throw NoSuchElementException("No id is provided or id is corrupted")

        return taskRepository.save(task)
    }

    @Transactional
    @Throws(NoSuchElementException::class, DbActionExecutionException::class)
    fun updateTaskName(id: String, name: String): Task {
        if (taskRepository.findById(UUID.fromString(id)).isEmpty)
            throw NoSuchElementException("No id is provided or id is corrupted")

        taskRepository.updateNameById(name, LocalDateTime.now(), UUID.fromString(id))

        return taskRepository.findById(UUID.fromString(id)).get()
    }

    @Transactional
    @Throws(NoSuchElementException::class)
    fun updateTaskDescription(id: String, description: String): Task {
        if (taskRepository.findById(UUID.fromString(id)).isEmpty)
            throw NoSuchElementException("No id is provided or id is corrupted")

        taskRepository.updateDescriptionById(description, LocalDateTime.now(), UUID.fromString(id))

        return taskRepository.findById(UUID.fromString(id)).get()
    }

    @Transactional
    @Throws(NoSuchElementException::class)
    fun updateTaskDone(id: String, isDone: Boolean): Task {
        if (taskRepository.findById(UUID.fromString(id)).isEmpty)
            throw NoSuchElementException("No id is provided or id is corrupted")

        taskRepository.updateIsDoneBy(isDone, LocalDateTime.now(), UUID.fromString(id))

        return taskRepository.findById(UUID.fromString(id)).get()
    }

    fun dropTable() = taskRepository.deleteAll()

    data class Page<T>(
        val content: List<T>,
        val currentPage: Int,
        val totalPages: Int
    )
}