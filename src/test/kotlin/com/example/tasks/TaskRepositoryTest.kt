package com.example.tasks

import com.example.tasks.dao.TaskRepository
import com.example.tasks.dao.models.Task
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class TaskRepositoryTest(
    @Autowired
    private val taskRepository: TaskRepository
) {

//    @BeforeEach
//    fun setUp() = taskRepository.deleteAll()

    @Test
    fun canSave() {
        val task = Task(
            name = "creating task test",
            creationDate = LocalDateTime.now()
        )

        assertThat(task.id).isNull()

        taskRepository.save(task)

        assertThat(task.id).isNotNull()

        val reloaded = taskRepository.findById(task.id!!).get()
        assertThat(reloaded.name).isEqualTo(task.name)
    }
}