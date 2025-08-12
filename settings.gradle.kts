enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
}

rootProject.name = "SuggestionsBot"

/* Subprojects */

includeLib("common")

includeService("bot")
includeService("worker")

/*
* Functions for including libraries and services.
* These functions cannot be moved to build-logic because they rely on
* the `Settings` object, which is not available in build-logic.
*/
//<editor-fold desc="Functions" defaultstate="collapsed">

/**
 * Includes a library subproject with the given name.
 * The source directory is expected to be in `lib/<libName>`, and the project
 * path will be `:SuggestLib-<libName>`.
 *
 * @param libName The name of the library to include.
 */
fun Settings.includeLib(libName: String) {
    val path = ":SuggestLib-$libName"

    include(path)
    project(path).projectDir = File("lib/$libName")
}

/**
 * Includes a service subproject with the given name.
 * The source directory is expected to be in `service/<serviceName>`, and the
 * project path will be `:SuggestService-<serviceName>`.
 *
 * @param serviceName The name of the service to include.
 */
fun Settings.includeService(serviceName: String) {
    val path = ":SuggestService-$serviceName"

    include(path)
    project(path).projectDir = File("service/$serviceName")
}

//</editor-fold>
