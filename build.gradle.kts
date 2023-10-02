import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"

    id("org.flywaydb.flyway") version "9.22.1"
}

flyway {
    url = "jdbc:postgresql://localhost:5432/postgres"
    user = "postgres"
    password = "1234"
    cleanDisabled = false
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
//    implementation("ch.qos.logback:logback-classic:1.2.9")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("org.flywaydb:flyway-core")
    implementation("org.webjars.npm:htmx.org:1.9.6")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")

//    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    testImplementation("io.rest-assured:spring-mock-mvc:3.0.0")
    testImplementation("io.rest-assured:rest-assured:5.3.2")
    testImplementation("io.rest-assured:json-path:5.3.2")
    testImplementation("io.rest-assured:json-schema-validator:5.3.2")
    testImplementation("io.rest-assured:spring-mock-mvc:5.3.2")
    testImplementation("io.rest-assured:spring-web-test-client:5.3.2")
    testImplementation("io.rest-assured:kotlin-extensions:5.3.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.17.6")
    testImplementation("org.testcontainers:testcontainers:1.19.0")
    testImplementation("org.testcontainers:junit-jupiter:1.19.0")
    implementation("org.apache.groovy:groovy-all:4.0.15")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
