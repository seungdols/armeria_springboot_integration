import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    val KOTLIN = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutineVersion}",
        "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinVersion}",
    )

    val ARMERIA = listOf(
        "com.linecorp.armeria:armeria:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-bom:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-kotlin:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-spring-boot3-starter:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-spring-boot3-autoconfigure:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-spring-boot3-actuator-starter:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-tomcat10:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-grpc:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-grpc-kotlin:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-protobuf:${Versions.armeriaVersion}",
        "com.linecorp.armeria:armeria-logback:${Versions.armeriaVersion}",
    )

    val GRPC = listOf(
        "com.google.protobuf:protobuf-kotlin:${Versions.protobufVersion}",
        "io.grpc:grpc-protobuf:${Versions.grpcVersion}",
        "io.grpc:grpc-netty-shaded:${Versions.grpcVersion}",
        "io.grpc:grpc-kotlin-stub:${Versions.grpcKotlinVersion}",
    )

    val JACKSON = listOf(
        "com.fasterxml.jackson.module:jackson-module-kotlin",
        "org.jetbrains.kotlin:kotlin-reflect",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jacksonDatatypeJsr310Version}",
    )

    val TEST = listOf(
        "org.springframework.boot:spring-boot-starter-test",
        "org.jetbrains.kotlin:kotlin-test",
        // kotest
        "io.kotest:kotest-runner-junit5:${Versions.kotestVersion}",
        "io.kotest:kotest-assertions-core:${Versions.kotestVersion}",
        "io.kotest.extensions:kotest-extensions-spring:${Versions.kotestExtensionSpringVersion}",
        // mockk
        "io.mockk:mockk:${Versions.mockkVersion}",
    )

    const val SPRING_BOOT_CONFIGURATION_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor"
    const val LOGGING = "io.github.oshai:kotlin-logging-jvm:${Versions.kotlinLoggingJvmVersion}"
}

fun DependencyHandler.api(dependencies: List<Any>) {
    dependencies.forEach {
        add("api", it)
    }
}

fun DependencyHandler.implementation(dependencies: List<Any>) {
    dependencies.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.compileOnly(dependencies: List<Any>) {
    dependencies.forEach {
        add("compileOnly", it)
    }
}

fun DependencyHandler.testImplementation(dependencies: List<String>) {
    dependencies.forEach {
        add("testImplementation", it)
    }
}

fun DependencyHandler.kapt(dependencies: List<String>) {
    dependencies.forEach {
        add("kapt", it)
    }
}
