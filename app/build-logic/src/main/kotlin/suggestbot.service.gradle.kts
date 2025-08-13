plugins {
    id("suggestbot.base")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    annotationProcessor(libs.spring.configuration.processor)
    testImplementation(libs.spring.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks {
    jar {
        enabled = false
    }

    bootJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
        destinationDirectory.set(file("$rootDir/build/service"))
    }

    test {
        useJUnitPlatform()
    }
}
