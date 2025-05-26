plugins {
    id("buildlogic.kotlin-application-conventions")
}

dependencies {
    implementation("org.apache.commons:commons-text:1.13.0")
    implementation(project(":utilities"))
}

application {
    // Define the main class for the application.
    mainClass = "org.example.app.AppKt"
}
