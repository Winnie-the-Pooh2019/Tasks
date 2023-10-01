package com.example.tasks.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PersistenceConfig {

    @Bean
    fun getLogger() = KotlinLogging.logger {  }
}