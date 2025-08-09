import gradle.kotlin.dsl.accessors._0cb39c16b209519d61ee18b0fceac003.test
import gradle.kotlin.dsl.accessors._0cb39c16b209519d61ee18b0fceac003.testRuntimeOnly
import org.gradle.kotlin.dsl.invoke

plugins {
    id("suggestbot.shadow")
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

    shadowJar {
        destinationDirectory.set(file("$rootDir/build/platform"))

        archiveClassifier.set("")
    }

    test {
        useJUnitPlatform()
    }
}
