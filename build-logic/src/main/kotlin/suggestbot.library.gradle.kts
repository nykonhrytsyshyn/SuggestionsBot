import org.gradle.kotlin.dsl.invoke

plugins {
    id("suggestbot.base")
    id("com.gradleup.shadow")
}

dependencies {
    runtimeClasspath(libs.spring.web)
    runtimeClasspath(libs.spring.data.jpa)
    runtimeClasspath(libs.spring.data.mongodb)

    compileOnly(libs.spring.configuration.processor)
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
