package com.example.tasks.domain.repository

import com.example.tasks.domain.models.Task
import io.github.oshai.kotlinlogging.KLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*
import kotlin.NoSuchElementException

@SpringBootTest
@Testcontainers
@Profile("test")
class TaskRepositoryTest(
    @Autowired
    private val taskRepository: TaskRepository,
    @Autowired
    private val logger: KLogger
) {

    @BeforeEach
    fun setUp() = taskRepository.deleteAll()

    @Test
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
    fun canNotSaveTheSame() {
        val task = Task(
            name = "creating task test",
            creationDate = Task.now()
        )

        taskRepository.save(task)

        val message = assertThrows<DbActionExecutionException> { taskRepository.save(task) }

        logger.warn { "Exception message: ${message.stackTraceToString()}" }

        assertThat(message).isNotNull()
    }

    @Test
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
    fun canNotUpdateName() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )
        val newName = "new name"

        val fakeId = UUID.randomUUID()
        taskRepository.save(task)
        taskRepository.updateNameById(newName, Task.now(), fakeId)
        val message = assertThrows<Exception> { taskRepository.findById(fakeId).get() }

        logger.warn { message }
    }

    @Test
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

    // no such element exception
    @Test
    fun canNotUpdateDone() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )

        val fakeId = UUID.randomUUID()
        taskRepository.save(task)
        taskRepository.updateIsDoneBy(true, Task.now(), fakeId)
        val message = assertThrows<NoSuchElementException> { taskRepository.findById(fakeId).get() }

        logger.warn { message }
    }

    @Test
    fun updateSaveTest() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )

        val saved = taskRepository.save(task)

        logger.warn { saved }

        val newTask = Task(
            id = saved.id,
            name = "new name",
            description = "new description",
            creationDate = saved.creationDate
        )

        logger.warn { taskRepository.save(newTask) }
    }
}