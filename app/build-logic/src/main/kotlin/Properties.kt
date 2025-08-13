@file:Suppress("unused")

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion

/** The UTF-8 charset name constant. */
val utf8: String = Charsets.UTF_8.name()

//<editor-fold desc="Project Properties" defaultstate="collapsed">

/**
 * Retrieves the Java version as an integer from the root project's properties.
 *
 * @throws IllegalStateException If the "java.version" property is not set.
 */
val Project.javaVersionInt: Int
    get() = (
                getProperty("java.version")
                ?: throw IllegalStateException("Java version not set")
            ).toString().toInt()

/**
 * Retrieves the JavaVersion object corresponding to the Java version integer.
 */
val Project.javaVersion: JavaVersion
    get() = JavaVersion.toVersion(javaVersionInt)

/**
 * Retrieves the JavaLanguageVersion corresponding to the Java version integer.
 */
val Project.javaLanguageVersion: JavaLanguageVersion
    get() = JavaLanguageVersion.of(javaVersionInt)

/**
 * Retrieves Java compiler arguments from the root project's properties.
 *
 * @throws IllegalStateException if the "java.compilerArgs" property is not set.
 */
val Project.javaCompilerArgs: String
    get() = (
                getProperty("java.compilerArgs")
                ?: throw IllegalStateException("Java compiler args not set")
            ).toString()

/**
 * Retrieves the project group from the root project's properties.
 *
 * @throws IllegalStateException if the "app.group" property is not set.
 */
val Project.projectGroup: String
    get() = (
                getProperty("app.group")
                ?: throw IllegalStateException("Project group not set")
            ).toString()

/**
 * Retrieves the project version from the root project's properties.
 *
 * @throws IllegalStateException if the "app.version" property is not set.
 */
val Project.projectVersion: Any
    get() = (
                getProperty("app.version")
                ?: throw IllegalStateException("Project version not set")
            )

/**
 * Retrieves the project description from the root project's properties.
 *
 * @throws IllegalStateException if the "app.description" property is not set.
 */
val Project.projectDescription: String
    get() = (
                getProperty("app.description")
                ?: throw IllegalStateException("Project description not set")
            ).toString()

//</editor-fold>

/**
 * Retrieves a property by name from the root project.
 *
 * @param name The name of the property.
 * @return The property value or null if not found.
 */
fun Project.getProperty(name: String): Any? {
    return rootProject.property(name)
}

/**
 * Retrieves a property by name from the root project with a default value.
 *
 * @param name    The name of the property.
 * @param default The default value returned if property is not found.
 * @return The property value or the default value.
 */
fun Project.getProperty(
    name: String,
    default: Any
): Any {
    return rootProject.property(name) ?: default
}
