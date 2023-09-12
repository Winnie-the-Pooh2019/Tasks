package com.example.tasks.domain

import com.example.tasks.domain.models.Task
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface TaskRepository : CrudRepository<Task, Int> {

    @Query("select * from tasks where id = :id")
    fun findById(id: String): Optional<Task>

    @Query("select * from tasks where name = :name")
    fun findByName(name: String): Optional<Task>

    @Query("select * from tasks where creation_date = :creationDate")
    fun findAllByCreationDate(creationDate: LocalDateTime): Optional<List<Task>>

    @Query("select * from tasks where is_done = :isDone")
    fun findAllByDone(isDone: Boolean): Optional<List<Task>>

    @Query("update tasks set name = :name, modification_date = :modificationDate where id = :id")
    fun updateNameById(name: String, modificationDate: LocalDateTime, id: String)

    @Query("update tasks set description = :description, modification_date = :modificationDate where id = :id")
    fun updateDescriptionById(name: String, modificationDate: LocalDateTime, id: String)

    @Query("update tasks set is_done = :is_done, modification_date = :modificationDate where id = :id")
    fun updateIsDoneBy(isDone: Boolean, modificationDate: LocalDateTime, id: String)
}