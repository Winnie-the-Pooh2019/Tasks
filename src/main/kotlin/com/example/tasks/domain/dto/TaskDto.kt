package com.example.tasks.domain.dto

import com.example.tasks.domain.models.Task
import java.time.LocalDateTime

data class TaskDto(
    val id: String? = null,
    val name: String,
    val description: String? = null,
    val creationDate: String,
    val modificationDate: String? = null,
    val isDone: Boolean = false
) {
    fun toModel(): Task = Task(
        id = id,
        name = name,
        description = description,
        creationDate = LocalDateTime.parse(creationDate),
        modificationDate = LocalDateTime.parse(modificationDate),
        isDone = isDone
    )
}
