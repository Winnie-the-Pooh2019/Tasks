package com.example.tasks.config

import com.example.tasks.dao.models.Task
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import java.util.*


@Configuration
class PersistenceConfig {
    @Bean
    fun beforeConvertCallback(): BeforeConvertCallback<Task> {
        return BeforeConvertCallback<Task> { task: Task ->
            if (task.id == null)
                return@BeforeConvertCallback Task(
                    UUID.randomUUID().toString(),
                    task.name,
                    task.description,
                    task.creationDate,
                    task.modificationDate,
                    task.isDone
                )

            task
        }
    }
}