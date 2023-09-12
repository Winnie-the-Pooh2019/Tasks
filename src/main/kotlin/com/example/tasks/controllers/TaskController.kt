package com.example.tasks.controllers

import com.example.tasks.domain.TaskRepository
import com.example.tasks.domain.dto.TaskDto
import com.example.tasks.domain.models.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/task")
class TaskController(
    @Autowired
    private val taskRepository: TaskRepository
){
    @PostMapping("create")
    @ResponseBody
    fun createTask(@RequestBody taskDto: TaskDto): Task {
        val task = taskDto.toModel()

        return taskRepository.save(task)
    }

    @PutMapping("put")
    @ResponseBody
    fun putTask(@RequestBody taskDto: TaskDto): Task {
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

    @PatchMapping("patch/name")
    @ResponseBody
    fun patchNameTask(@RequestParam id: String, @RequestBody name: String): Task {
        taskRepository.updateNameById(name, LocalDateTime.now(), id)

        return taskRepository.findById(id).get()
    }

    @PatchMapping("patch/description")
    @ResponseBody
    fun patchDescriptionTask(@RequestParam id: String, @RequestBody description: String): Task {
        taskRepository.updateDescriptionById(description, LocalDateTime.now(), id)

        return taskRepository.findById(id).get()
    }

    @PatchMapping("patch/done")
    @ResponseBody
    fun patchDoneTask(@RequestParam id: String, @RequestBody isDone: Boolean): Task {
        taskRepository.updateIsDoneBy(isDone, LocalDateTime.now(), id)

        return taskRepository.findById(id).get()
    }
}