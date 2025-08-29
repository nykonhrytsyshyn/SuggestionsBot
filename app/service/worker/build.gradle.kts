plugins {
    alias(libs.plugins.suggestbot.service)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(libs.telegram.client)

    implementation(libs.micrometer.prometheus)

    implementation(libs.spring.actuator)
    implementation(libs.spring.amqp)
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.data.redis)
    implementation(libs.spring.rabbitmq.test)
    implementation(libs.spring.web)

    developmentOnly(libs.spring.devtools)

    runtimeOnly(libs.postgresql)
}
