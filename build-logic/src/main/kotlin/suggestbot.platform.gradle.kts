plugins {
    id("suggestbot.base")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.data.mongodb)
    runtimeOnly(libs.postgresql)

    annotationProcessor(libs.spring.configuration.processor)
    testImplementation(libs.spring.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks {
    jar {
        enabled = false
    }

    bootJar {
        archiveFileName.set("${project.name}-${version}.jar")
        destinationDirectory.set(file("$rootDir/build/platform"))
    }

    test {
        useJUnitPlatform()
    }
}
