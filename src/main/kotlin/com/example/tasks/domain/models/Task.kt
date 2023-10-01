package com.example.tasks.domain.models

import com.example.tasks.domain.dto.TaskDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("tasks")
data class Task(
    @Id
    val id: UUID? = null,

    val name: String,

    val description: String? = null,

    @Column("creation_date")
    val creationDate: LocalDateTime,

    @Column("modification_date")
    val modificationDate: LocalDateTime? = null,

    @Column("is_done")
    val isDone: Boolean = false
) {
    companion object {
        fun now(): LocalDateTime {
            val now = LocalDateTime.now()

            return now.minusNanos(now.nano.toLong())
        }
    }

    fun toDto() = TaskDto(
        id = id.toString(),
        name = name,
        description = description,
        creationDate = creationDate.toString(),
        modificationDate = modificationDate.toString(),
        isDone = isDone
    )
}
