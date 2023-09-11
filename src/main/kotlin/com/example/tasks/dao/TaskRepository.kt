package com.example.tasks.dao

import com.example.tasks.dao.models.Task
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
}