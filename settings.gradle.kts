enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "SuggestionsBot"

includeLib("common")

includePlatform("bot")
includePlatform("db")

fun Settings.includeLib(libName: String) {
    val path = ":SuggestLib-$libName"

    include(path)
    project(path).projectDir = File("lib/${libName}")
}

fun Settings.includePlatform(platformName: String) {
    val path = ":SuggestPlatform-$platformName"

    include(path)
    project(path).projectDir = File("platform/$platformName")
}
