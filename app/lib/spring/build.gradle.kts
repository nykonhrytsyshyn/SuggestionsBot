plugins {
    alias(libs.plugins.suggestbot.library)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(platform(libs.spring.dependencies))

    implementation(libs.spring.actuator)
    implementation(libs.spring.amqp)
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.data.redis)
}
