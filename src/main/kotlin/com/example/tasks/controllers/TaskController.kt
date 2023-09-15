package com.example.tasks.controllers

import com.example.tasks.domain.dto.TaskDto
import com.example.tasks.domain.models.Task
import com.example.tasks.domain.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/task")
class TaskController(
    @Autowired
    private val taskService: TaskService
) {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElement(exception: NoSuchElementException) = ResponseEntity(exception.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(DbActionExecutionException::class)
    fun handleIllegalArgument(exception: IllegalArgumentException) = ResponseEntity(exception.message, HttpStatus.CONFLICT)

    @PostMapping("create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody taskDto: TaskDto): Task = taskService.createTask(taskDto)

    @PutMapping("put")
    @ResponseBody
    fun putTask(@RequestBody taskDto: TaskDto): Task = taskService.updateTask(taskDto)

    @PatchMapping("patch/name")
    @ResponseBody
    fun patchNameTask(@RequestParam id: String, @RequestBody name: String): Task = taskService.updateTaskName(id, name)

    @PatchMapping("patch/description")
    @ResponseBody
    fun patchDescriptionTask(@RequestParam id: String, @RequestBody description: String): Task =
        taskService.updateTaskDescription(id, description)

    @PatchMapping("patch/done")
    @ResponseBody
    fun patchDoneTask(@RequestParam id: String, @RequestBody isDone: Boolean): Task =
        taskService.updateTaskDone(id, isDone)
}