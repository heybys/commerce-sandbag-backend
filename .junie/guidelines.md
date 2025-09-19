# Commerce Sandbag Backend — Project Guidelines

## Overview
This repository is a Kotlin/JVM multi-module Gradle project. The main `app` module is a Spring Boot 3 service (Java 21) using MySQL and Liquibase for migrations. Supporting modules include reusable utilities and a small data-structure library used for demonstration/tests.

Key technologies:
- Kotlin 2.2, JVM Toolchain 21
- Spring Boot 3 (web, validation, data-jpa, security, OAuth2)
- Liquibase for DB schema migrations
- MySQL (via Docker Compose for local dev)
- JUnit 5 + kotlin-test
- Spotless + ktlint for formatting

## Project Structure
- app/ — Spring Boot application
  - src/main/kotlin/org/example/app/Application.kt — Application entry point
  - src/main/resources/application.properties — App configuration (JDBC URL, Liquibase, OAuth2, logging)
  - src/main/resources/changelog/** — Liquibase changelogs; db.changelog-master.xml is the master file
  - src/main/resources/keys/** — Local dev key material (signing/encryption)
  - build.gradle.kts — Application dependencies and plugins (Spring Boot, Kotlin plugins, Spotless)
- utilities/ — Shared Kotlin utilities used by the app
- list/ — Example Kotlin library (linked list) with tests
- buildSrc/ — Centralized Gradle convention plugins for the project
- gradle/ and gradlew* — Gradle wrapper
- compose.yaml — Local MySQL service for development

## Prerequisites
- JDK 21 available (project uses Gradle toolchains but JDK installation is recommended)
- Docker (optional, for running local MySQL via Compose)

## How to Build
From the repository root:
- Clean and build all modules: `./gradlew clean build`
  - The build is configured to auto-apply formatting (Spotless) via `spotlessKotlinApply`.
- Format Kotlin sources explicitly (optional): `./gradlew spotlessApply`

## How to Run the Application
1) Start local MySQL (optional if you already have MySQL running):
- `docker compose up -d` (uses compose.yaml with DB name `master` and credentials in application.properties)

2) Run the Spring Boot app:
- From root: `./gradlew :app:bootRun`
- Or build and run the jar: `./gradlew :app:bootJar` then `java -jar app/build/libs/app-*.jar`

Notes:
- Liquibase will apply migrations at startup using `spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.xml`.
- Some OAuth2 env vars are referenced (e.g., `GOOGLE_CLIENT_ID`, `GOOGLE_CLIENT_SECRET`). They are only required if you exercise OAuth login flows.

## How to Run Tests
- All modules: `./gradlew test`
- Single module tests: `./gradlew :app:test`, `./gradlew :list:test`, `./gradlew :utilities:test`
- You can run a single test class using Gradle test filtering, e.g.: `./gradlew :list:test --tests "org.example.list.LinkedListTest"`

## Code Style & Conventions
- Kotlin style is enforced by Spotless + ktlint (indent size 4). Run `./gradlew spotlessApply` to auto-format.
- Kotlin compiler uses `-Xjsr305=strict` for nullability interoperability.
- Use idiomatic Kotlin and prefer immutable data where possible.
- Keep module boundaries clear: generic helpers in `utilities`, domain/app logic in `app`.

## Junie-Specific Guidance
When making changes:
- Always run unit tests relevant to the touched modules. Prefer `./gradlew test` for a full check.
- Ensure the project builds before submitting the result: `./gradlew build`.
- If touching database-related code or changelogs, verify the app can start locally with MySQL via `docker compose up -d` and `./gradlew :app:bootRun`.
- Respect formatting; if edits are made, run `./gradlew spotlessApply` or rely on the build to apply formatting.

## Troubleshooting
- Port conflicts for MySQL: stop local MySQL or adjust compose.yaml/`application.properties`.
- Missing JDK 21: install or configure Gradle toolchains properly.
- If Spring Boot fails on OAuth env vars, set dummy values during local dev or avoid OAuth flows.
