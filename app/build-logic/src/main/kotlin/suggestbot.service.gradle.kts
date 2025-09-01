plugins {
    id("suggestbot.base")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    compileOnlyApi(SubProjects.SpringLib.asProject(rootProject))

    annotationProcessor(libs.spring.configuration.processor)

    testImplementation(libs.spring.test)
}

sourceSets {
    main {
        java.srcDir(SubProjects.SpringLib.asProject(rootProject).sourceSets.main.get().java.srcDirs)
    }
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
