plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
    `maven-publish`
}

group = "eu.qwsome"
version = "1.0.0-SNAPSHOT"
description = "The raw sql builder"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.stdlib)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj.core)
    testImplementation(libs.jmh.core)
    testAnnotationProcessor(libs.jmh.processor)
}

sourceSets {
    main {
        kotlin.srcDirs("src/main/kotlin", "src/main/java")
        java.srcDirs("src/main/java")
    }
    test {
        kotlin.srcDirs("src/test/kotlin", "src/test/java")
        java.srcDirs("src/test/java")
    }
}

tasks.test {
    useJUnitPlatform()
}

// Configure Kotlin compilation to happen after Java compilation
tasks.compileKotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

tasks.compileTestKotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set("The raw sql builder")
                description.set("A lightweight SQL query builder library for Java that provides a fluent API for constructing SQL/JPQL/HQL queries")
                url.set("https://github.com/sgflt/sqlbuilder")

                scm {
                    url.set("https://github.com/sgflt/sqlbuilder.git")
                    tag.set("HEAD")
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/sgflt/sqlbuilder/issues")
                }

                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        name.set("Lukáš Kvídera")
                        email.set("kvideral@qwsome.eu")
                        timezone.set("CET")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/sgflt/sqlbuilder")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
