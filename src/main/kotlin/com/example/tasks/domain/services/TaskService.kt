package com.example.tasks.domain.services

import com.example.tasks.domain.TaskRepository
import com.example.tasks.domain.dto.TaskDto
import com.example.tasks.domain.models.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TaskService(
    @Autowired
    private val taskRepository: TaskRepository
) {
    fun createTask(taskDto: TaskDto): Task = taskRepository.save(taskDto.toModel())

    @Transactional
    fun updateTask(taskDto: TaskDto): Task {
        val task = taskRepository.findByName(taskDto.name).get()
        val newTask = Task(
            task.id,
            taskDto.name,
            taskDto.description,
            task.creationDate,
            LocalDateTime.now(),
            taskDto.isDone
        )

        taskRepository.delete(task)
        return taskRepository.save(newTask)
    }

    @Transactional
    fun updateTaskName(id: String, name: String): Task {
        taskRepository.updateNameById(name, LocalDateTime.now(), id)

        return taskRepository.findById(id).get()
    }

    @Transactional
    fun updateTaskDescription(id: String, description: String): Task {
        taskRepository.updateDescriptionById(description, LocalDateTime.now(), id)

        return taskRepository.findById(id).get()
    }

    @Transactional
    fun updateTaskDone(id: String, isDone: Boolean): Task {
        taskRepository.updateIsDoneBy(isDone, LocalDateTime.now(), id)

        return taskRepository.findById(id).get()
    }
}