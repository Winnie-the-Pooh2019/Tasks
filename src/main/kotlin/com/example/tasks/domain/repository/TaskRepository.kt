package com.example.tasks.domain.repository

import com.example.tasks.domain.models.Task
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface TaskRepository : CrudRepository<Task, Int> {

    @Query("select * from tasks where id = :id")
    fun findById(id: UUID): Optional<Task>

    @Query("select * from tasks where name = :name")
    fun findByName(name: String): Optional<Task>

    @Query("select * from tasks where is_done = :isDone")
    fun findAllByDone(isDone: Boolean): Optional<List<Task>>

    @Query("select count(*) from tasks")
    fun countRows(): Int

    @Query("select * from tasks order by id limit :limit offset :offset")
    fun findPaginatedTasks(@Param("limit") limit: Int, @Param("offset") offset: Int): Optional<List<Task>>

    @Modifying
    @Query("update tasks set name = :name, modification_date = :modificationDate where id = :id")
    fun updateNameById(name: String, modificationDate: LocalDateTime, id: UUID)

    @Modifying
    @Query("update tasks set description = :description, modification_date = :modificationDate where id = :id")
    fun updateDescriptionById(description: String, modificationDate: LocalDateTime, id: UUID)

    @Modifying
    @Query("update tasks set is_done = :isDone, modification_date = :modificationDate where id = :id")
    fun updateIsDoneBy(isDone: Boolean, modificationDate: LocalDateTime, id: UUID)

    @Modifying
    @Query("delete from tasks where id = :id")
    fun deleteById(id: UUID)
}