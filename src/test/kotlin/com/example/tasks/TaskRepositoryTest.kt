package com.example.tasks

import com.example.tasks.domain.TaskRepository
import com.example.tasks.domain.models.Task
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime

@SpringBootTest
@DataJdbcTest
class TaskRepositoryTest(
    @Autowired
    private val taskRepository: TaskRepository
) {

    @BeforeEach
    fun setUp() = taskRepository.deleteAll()

    @Test
    fun canSave() {
        val task = Task(
            name = "creating task test",
            creationDate = OffsetDateTime.now()
        )

        assertThat(task.id).isNull()

        val saved = taskRepository.save(task)

//        assertThat(task.id).isNotNull()

        val reloaded = taskRepository.findById(saved.id!!).get()
        assertThat(reloaded.name).isEqualTo(task.name)
    }
}