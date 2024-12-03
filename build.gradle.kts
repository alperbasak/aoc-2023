plugins {
    kotlin("jvm") version "2.1.0" apply false
}

group = "com.alperbasak"
version = "1.0-SNAPSHOT"


allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    dependencies {
        add("testImplementation", kotlin("test")) // Use 'add' for proper scoping
    }
    // Configure Kotlin settings
    extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        jvmToolchain(23) // Specify JVM toolchain
    }

    // Configure tasks
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}



