import org.gradle.kotlin.dsl.invoke

plugins {
    id("suggestbot.base")
    id("com.gradleup.shadow")
}

tasks {
    jar {
        destinationDirectory.set(file("$rootDir/build/lib"))
    }

    shadowJar {
        destinationDirectory.set(file("$rootDir/build/lib"))
    }

    build {
        dependsOn(shadowJar)
    }
}
