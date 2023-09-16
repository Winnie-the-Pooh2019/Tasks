package com.example.tasks

import com.example.tasks.domain.models.Task
import com.example.tasks.domain.repo.TaskRepository
import io.github.oshai.kotlinlogging.KLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class TaskRepositoryTest(
    @Autowired
    private val taskRepository: TaskRepository,
    @Autowired
    private val logger: KLogger
) {

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
    fun canNotUpdateName() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )
        val newName = "new name"

        taskRepository.save(task)
        taskRepository.updateNameById(newName, Task.now(), "fake id")
        val message = assertThrows<Exception> { taskRepository.findById("fake id").get() }

        logger.warn { message }
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

    @Test
    @Transactional
    fun canNotUpdateDone() {
        val task = Task(
            name = "I want to pass test",
            description = "description",
            creationDate = Task.now()
        )

        taskRepository.save(task)
        taskRepository.updateIsDoneBy(true, Task.now(), "fake id")
        val message = assertThrows<NoSuchElementException> { taskRepository.findById("fake id").get() }

        logger.warn { message }
    }

    @Test
    @Transactional
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