package com.example.tasks.dao.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class Task(
    @Id
    val id: String? = null,
    val name: String,
    val description: String? = null,
    @Column("creation_date")
    val creationDate: LocalDateTime,
    @Column("modification_date")
    val modificationDate: LocalDateTime? = null,
    @Column("is_done")
    val isDone: Boolean = false
) {

}
