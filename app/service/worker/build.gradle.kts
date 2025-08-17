plugins {
    id("suggestbot.service")
}

dependencies {
    implementation(libs.telegram.client)

    implementation(libs.spring.actuator)
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.data.redis)
    implementation(libs.spring.web)

    developmentOnly(libs.spring.devtools)

    runtimeOnly(libs.postgresql)
}
