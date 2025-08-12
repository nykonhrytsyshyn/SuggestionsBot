plugins {
    id("suggestbot.service")
}

dependencies {
    implementation(libs.telegram.client)

    implementation(libs.spring.data.jpa)
    implementation(libs.spring.data.redis)
    runtimeOnly(libs.postgresql)
}
