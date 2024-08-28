import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//tasks.bootJar { enabled = false }
//tasks.jar { enabled = true }

val rootProject = project

plugins {
    id("org.springframework.boot") version Versions.springBootVersion
    id("io.spring.dependency-management") version Versions.springDependencyManagementVersion
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlintVersion
    id("org.jetbrains.kotlin.plugin.noarg") version Versions.kotlinVersion
    id("org.jetbrains.kotlinx.kover") version Versions.koverVersion
    id("com.adarshr.test-logger") version Versions.adarshrTestLoggerVersion
    kotlin("kapt") version Versions.kotlinVersion
    kotlin("jvm") version Versions.kotlinVersion
    kotlin("plugin.spring") version Versions.kotlinVersion
}

allprojects {
    group = "com.seungdols.company"

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://repo.spring.io/snapshot")
        maven(url = "https://repo.spring.io/milestone")
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.kotlin.plugin.noarg")

        plugin("org.jetbrains.kotlinx.kover")
        plugin("com.adarshr.test-logger")
        plugin("idea")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_17.majorVersion
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        forkEvery = 100
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        implementation(Dependencies.KOTLIN)
        implementation(Dependencies.JACKSON)
        implementation(Dependencies.LOGGING)
        kapt(Dependencies.SPRING_BOOT_CONFIGURATION_PROCESSOR)
        testImplementation(Dependencies.TEST)
        // Add modules for kover
        kover(project(path))
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude { it.file.path.contains("generated") }
        }
    }
}

kover {
    useJacoco()
    // https://github.com/Kotlin/kotlinx-kover/blob/v0.8.0/docs/gradle-plugin/migrations/migration-to-0.8.0.md
    reports {
        total {
            xml {
                onCheck = true
                file(layout.buildDirectory.file("reports/kover/report.xml"))
            }
            verify {
                onCheck = false
                rule {
                    groupBy = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.APPLICATION

                    bound {
                        minValue = 80
                        coverageUnits = CoverageUnit.LINE
                        aggregationForGroup = AggregationType.COVERED_PERCENTAGE
                    }
                }
            }
        }
    }
}

testlogger {
    setTheme("mocha")
}
