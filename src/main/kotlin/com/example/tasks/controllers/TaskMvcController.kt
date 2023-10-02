package com.example.tasks.controllers

import com.example.tasks.domain.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TaskMvcController(
    @Autowired
    private val taskService: TaskService
) {
    @GetMapping("load/tasks/{page}")
    fun index(@PathVariable page: Int, model: Model): String {
        val taskPage = taskService.getPaginatedTasks(page, 10)

        model.addAttribute("tasks", taskPage.content)
        model.addAttribute("currentPage", taskPage.currentPage)
        model.addAttribute("totalPages", taskPage.totalPages)

        return "tasks :: task-list"
    }

    @GetMapping
    fun index(model: Model): String {
        val tasks = taskService.getAll()
        model.addAttribute("tasks", tasks)

        return "index"
    }
}