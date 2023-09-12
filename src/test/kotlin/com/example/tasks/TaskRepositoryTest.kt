package com.example.tasks

import com.example.tasks.domain.TaskRepository
import com.example.tasks.domain.models.Task
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.logging.Logger

@SpringBootTest
class TaskRepositoryTest(
    @Autowired
    private val taskRepository: TaskRepository
) {

    @BeforeEach
    @AfterEach
    fun setUp() = taskRepository.deleteAll()

    @Test
    @Transactional
    fun canSave() {
        val task = Task(
            name = "creating task test",
            creationDate = Task.now()
        )

        assertThat(task.id).isNull()

        val saved = taskRepository.save(task)

        val reloaded = taskRepository.findById(saved.id!!).get()
        assertThat(reloaded.name).isEqualTo(task.name)
    }

    @Test
    @Transactional
    fun canFindByName() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )

        val saved = taskRepository.save(task)
        val reloaded = taskRepository.findByName(saved.name).get()

        assertThat(saved).isEqualTo(reloaded)
    }

    @Test
    @Transactional
    fun canFindById() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )

        val saved = taskRepository.save(task)
        val reloaded = taskRepository.findById(saved.id!!).get()

        assertThat(saved).isEqualTo(reloaded)
    }

    @Test
    @Transactional
    fun canFindByDone() {
        val tasks = listOf(
            Task(name = "first", creationDate = Task.now()),
            Task(name = "second", creationDate = Task.now(), isDone = true),
            Task(name = "third", creationDate = Task.now()),
            Task(name = "forth", creationDate = Task.now(), isDone = true),
            Task(name = "fifth", creationDate = Task.now()),
            Task(name = "sixth", creationDate = Task.now(), isDone = true),
        )

        val saved = taskRepository.saveAll(tasks)

        val savedFalse = taskRepository.findAllByDone(false).get()
        val savedTrue = taskRepository.findAllByDone(true).get()

        assertThat(saved.filter { it.isDone }).isEqualTo(savedTrue)
        assertThat(saved.filter { !it.isDone }).isEqualTo(savedFalse)
    }

    @Test
    @Transactional
    fun canUpdateName() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )
        val newName = "new name"

        val saved = taskRepository.save(task)
        taskRepository.updateNameById(newName, Task.now(), saved.id!!)
        val newSaved = taskRepository.findById(saved.id!!).get()

        assertThat(newSaved.name).isEqualTo(newName)
    }

    @Test
    @Transactional
    fun canUpdateDescription() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )
        val newDescription = "new name"

        val saved = taskRepository.save(task)
        taskRepository.updateDescriptionById(newDescription, Task.now(), saved.id!!)
        val newSaved = taskRepository.findById(saved.id!!).get()

        assertThat(newSaved.description).isEqualTo(newDescription)
    }

    @Test
    @Transactional
    fun canUpdateDone() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )

        val saved = taskRepository.save(task)
        taskRepository.updateIsDoneBy(true, Task.now(), saved.id!!)
        val newSaved = taskRepository.findById(saved.id!!).get()

        assertThat(newSaved.isDone).isTrue()
    }
}