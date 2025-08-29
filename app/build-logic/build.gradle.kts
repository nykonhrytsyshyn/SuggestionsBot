plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(gradleApi())

    implementation(libs.plugin.shadow)
    implementation(libs.plugin.spring.boot)
    implementation(libs.plugin.spring.dependency.management)
}
