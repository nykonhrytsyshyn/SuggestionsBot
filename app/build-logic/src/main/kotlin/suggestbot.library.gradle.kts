plugins {
    id("suggestbot.base")
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks {
    jar {
        destinationDirectory.set(file("$rootDir/build/lib"))
    }

    test {
        useJUnitPlatform()
    }
}
