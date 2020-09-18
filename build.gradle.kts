import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    application
}

group = "com.zahiar"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation("software.amazon.awssdk:cloudtrail:2.14.16")
    implementation("org.slf4j:slf4j-simple:1.7.30")

    testImplementation(kotlin("test-junit5"))
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}
