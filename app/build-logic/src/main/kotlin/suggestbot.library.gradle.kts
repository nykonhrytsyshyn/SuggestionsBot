import gradle.kotlin.dsl.accessors._a252323f2477ad123f0aa5aba7a9238a.test
import gradle.kotlin.dsl.accessors._a252323f2477ad123f0aa5aba7a9238a.testImplementation
import gradle.kotlin.dsl.accessors._a252323f2477ad123f0aa5aba7a9238a.testRuntimeOnly
import org.gradle.kotlin.dsl.invoke

plugins {
    id("suggestbot.base")
    id("com.gradleup.shadow")
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
