package com.example.tasks.dao.models

import java.time.LocalDateTime

data class Task(
    val name: String,
    val description: String?,
    val creationDate: LocalDateTime,
    val modificationDate: LocalDateTime?,
    val done: Boolean
)
