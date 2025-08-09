plugins {
	id("suggestbot.platform")
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation(libs.telegram.client)
    implementation(libs.telegram.spring.longpolling)

    implementation(libs.spring.web)
    developmentOnly(libs.spring.devtools)
}
