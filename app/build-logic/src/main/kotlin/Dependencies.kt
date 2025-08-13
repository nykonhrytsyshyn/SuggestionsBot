@file:Suppress("unused")

import com.github.jengelman.gradle.plugins.shadow.internal.DependencyFilter
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.provider.Provider

/**
 * Excludes a dependency from the filter using a dependency provider.
 *
 * @param provider A provider of the dependency to exclude
 * @return The updated dependency filter or null if the dependency was not found
 */
fun DependencyFilter.exclude(provider: Provider<ExternalModuleDependency>): DependencyFilter? =
    exclude(provider.get())

/**
 * Excludes a dependency from the filter using the dependency object.
 * Uses the pattern group:name:.*
 *
 * @param dependency The dependency to exclude
 * @return The updated dependency filter or null if the dependency was not found
 */
fun DependencyFilter.exclude(dependency: ExternalModuleDependency): DependencyFilter? =
    exclude(dependency("${dependency.group}:${dependency.name}:.*"))

/**
 * Includes a dependency in the filter using a dependency provider.
 *
 * @param provider A provider of the dependency to include
 * @return The updated dependency filter or null if the dependency was not found
 */
fun DependencyFilter.include(provider: Provider<ExternalModuleDependency>): DependencyFilter? =
    include(provider.get())

/**
 * Includes a dependency in the filter using the dependency object.
 * Uses the pattern group:name:.*
 *
 * @param dependency The dependency to include
 * @return The updated dependency filter or null if the dependency was not found
 */
fun DependencyFilter.include(dependency: ExternalModuleDependency): DependencyFilter? =
    include(dependency("${dependency.group}:${dependency.name}:.*"))

