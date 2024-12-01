plugins {
    kotlin("jvm") version "2.1.0"
}

group = "com.alperbasak"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(23)
}

