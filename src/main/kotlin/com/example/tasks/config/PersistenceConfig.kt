package com.example.tasks.config

import com.example.tasks.domain.models.Task
import io.github.oshai.kotlinlogging.KotlinLogging
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
                    id = UUID.randomUUID(),
                    name = task.name,
                    description = task.description,
                    creationDate = task.creationDate,
                    modificationDate = task.modificationDate,
                    isDone = task.isDone
                )

            task
        }
    }

    @Bean
    fun getLogger() = KotlinLogging.logger {  }
}