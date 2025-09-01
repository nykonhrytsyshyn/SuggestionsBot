plugins {
    id("suggestbot.base")
    alias(libs.plugins.shadow)
}

tasks {
    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")
        destinationDirectory.set(tasks.jar.get().destinationDirectory.get())
    }

    build {
        dependsOn(shadowJar)
    }
}
