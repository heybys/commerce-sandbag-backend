plugins {
    id("buildlogic.kotlin-application-conventions")

    id("org.jetbrains.kotlin.plugin.spring") version "2.2.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "2.2.10"

    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.diffplug.spotless") version "6.21.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.liquibase:liquibase-core")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(project(":utilities"))
}

application {
    // Define the main class for the application.
    mainClass = "org.example.app.ApplicationKt"
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

spotless {
    kotlin {
        trimTrailingWhitespace()
        endWithNewline()
        ktlint().userData(
            mapOf(
                "indent_size" to "4"
            )
        )
    }
}

tasks.named("build") {
    dependsOn("spotlessKotlinApply")
}
