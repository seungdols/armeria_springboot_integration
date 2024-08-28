ext {
    set("mainClass", "com.seungdols.company.api.ApiApplicationKt")
}

dependencies {
    implementation(kotlin("reflect"))

    implementation(project(":protobufs"))

    implementation(Dependencies.GRPC)
    implementation(Dependencies.ARMERIA)
}

tasks.koverXmlReport {
    enabled = true
}

tasks.koverVerify {
    enabled = true
}
