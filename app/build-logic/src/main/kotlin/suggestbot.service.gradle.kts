plugins {
    id("suggestbot.base")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    annotationProcessor(libs.spring.configuration.processor)
    testImplementation(libs.spring.test)
}

springBoot {
    buildInfo {
        properties {
            name = project.name
            version = project.version.toString()
            description = project.description
        }
    }
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
