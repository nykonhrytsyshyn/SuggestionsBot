plugins {
    id("suggestbot.base")
    alias(libs.plugins.shadow)
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks {
    jar {
        destinationDirectory.set(file("$rootDir/build/lib"))
    }

    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
        destinationDirectory.set(file("$rootDir/build/lib"))
    }

    test {
        useJUnitPlatform()
    }

    build {
        dependsOn(shadowJar)
    }
}
