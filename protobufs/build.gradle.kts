import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }

plugins {
    id("com.google.protobuf") version Versions.protobufPluginVersion
}

dependencies {
    implementation(Dependencies.GRPC)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${Versions.protobufVersion}"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${Versions.grpcVersion}"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${Versions.grpcKotlinVersion}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
                create("grpckt")
            }
            task.builtins {
                create("kotlin")
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.majorVersion
}
