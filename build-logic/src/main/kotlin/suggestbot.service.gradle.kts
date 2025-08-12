plugins {
    id("suggestbot.base")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val prodBuild = project.hasProperty("prod")
                && project.property("prod") == "true"

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
        if (prodBuild) {
            archiveFileName.set("${project.name}.jar")
            destinationDirectory.set(file("$rootDir/build/production"))
        } else {
            archiveFileName.set("${project.name}-${version}.jar")
            destinationDirectory.set(file("$rootDir/build/service"))
        }
    }

    test {
        useJUnitPlatform()
    }
}
