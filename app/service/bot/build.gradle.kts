plugins {
    id("suggestbot.service")
}

dependencies {
    implementation(libs.telegram.client)
    implementation(libs.telegram.spring.longpolling)

    implementation(libs.spring.actuator)
    implementation(libs.spring.data.redis)
    implementation(libs.spring.web)

    developmentOnly(libs.spring.devtools)
}
