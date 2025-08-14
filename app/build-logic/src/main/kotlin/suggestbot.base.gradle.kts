plugins {
    `java-library`
}

group       = projectGroup
version     = projectVersion
description = projectDescription

java {
    toolchain {
        languageVersion.set(javaLanguageVersion)
    }

    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    if (project.name != SubProjects.Common.projectName) {
        compileOnlyApi(SubProjects.Common.asProject(rootProject))
    }

    implementation(libs.fastutil)
    implementation(libs.google.guava)
    implementation(libs.google.jsr305)
    implementation(libs.jetbrains.annotations)
}

sourceSets {
    main {
        java.srcDir(SubProjects.Common.asProject(rootProject).sourceSets.main.get().java.srcDirs)
    }
}

tasks {
    compileJava {
        options.encoding = utf8

        options.release.set(javaVersionInt)
        options.compilerArgs.add(javaCompilerArgs)
    }

    javadoc {
        options.encoding = utf8
        destinationDir = file("$rootDir/build/javadoc")
    }

    processResources {
        filteringCharset = utf8
    }
}
