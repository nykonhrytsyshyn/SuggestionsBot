plugins {
    id("suggestbot.platform")
}

dependencies {
    implementation(libs.telegram.client)
    implementation(libs.telegram.spring.longpolling)

    implementation(libs.spring.web)
    developmentOnly(libs.spring.devtools)
}
