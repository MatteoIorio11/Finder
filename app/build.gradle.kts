/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.12.1/userguide/building_java_projects.html in the Gradle documentation.
 */
import java.nio.file.Files
import java.nio.file.Paths

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.github.johnrengelman.shadow") version "8.1.1" // Use the latest version
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation(libs.junit)
    implementation(libs.okhttp)
    implementation(libs.jsoup)
    implementation(libs.dotenv)
    implementation(libs.slf4j.api)
    implementation(libs.guava)
    implementation(libs.logback.classic)
    testImplementation(libs.jupiter.api)
    testRuntimeOnly(libs.jupiter.engine)
    implementation(libs.commons.text)

}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.register("cloneTestRepository") {
    doLast {
        val homeDir = System.getProperty("user.home")
        val repoDir = Paths.get(homeDir, "FinderTest")

        if (Files.exists(repoDir)) {
            println("Repository already exists at $repoDir. Skipping clone.")
            return@doLast
        }

        val isWindows = System.getProperty("os.name").startsWith("Windows")

        val command = if (isWindows) {
            listOf("cmd", "/c", "git clone git@github.com:MatteoIorio11/FinderTest.git && move FinderTest \"$homeDir\"")
        } else {
            listOf("sh", "-c", "git clone git@github.com:MatteoIorio11/FinderTest.git && mv FinderTest \"$homeDir\"")
        }

        val process = ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()

        val exitCode = process.waitFor()
        if (exitCode != 0) {
            throw RuntimeException("Shell command failed with exit code $exitCode")
        }
    }
}

tasks.test {
    dependsOn("cloneTestRepository")
    useJUnitPlatform()
}

tasks.register<JavaExec>("runWithGUI") {
    mainClass.set("org.iorio.gui.GUI")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.jar {
    archiveBaseName.set("github-difference-finder")
    archiveVersion.set("1.0")
}

tasks {
    shadowJar {
        archiveBaseName.set("github-difference-finder")
        archiveVersion.set("1.0")
    }
}

application {
    // Define the main class for the application.
    mainClass = "org.iorio.App"
}
