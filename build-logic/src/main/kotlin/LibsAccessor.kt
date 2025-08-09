import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Accessor for the `libs` extension in Gradle build-logic. It provides access
 * to the libraries defined in the `libs.versions.toml` file.
 */
val Project.libs: LibrariesForLibs
    get() = rootProject.extensions.getByType()
