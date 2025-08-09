@file:Suppress("unused")

import org.gradle.api.Project

//<editor-fold desc="Constants" defaultstate="collapsed">

const val LIB_PREFIX = "SuggestLib"
const val LIB_DIR    = "lib"

const val PLATFORM_PREFIX = "SuggestPlatform"
const val PLATFORM_DIR    = "platform"

//</editor-fold>

/**
 * Represents a type of subproject in the Gradle build.
 *
 * Each type has:
 * - **prefix** — used in the Gradle project name.
 * - **baseDir** — directory where the project resides.
 *
 * @property prefix  The prefix for the project name.
 * @property baseDir The base directory where the project is located.
 *
 * @see [ProjectType.LIB]
 * @see [ProjectType.PLATFORM]
 */
enum class ProjectType(
    val prefix: String,
    val baseDir: String
) {
    //<editor-fold desc="Project Types" defaultstate="collapsed">

    /**
     * Represents a library subproject.
     *
     * Properties:
     * - **prefix** — [LIB_PREFIX]
     * - **baseDir** — [LIB_DIR]
     */
    LIB(LIB_PREFIX, LIB_DIR),
    /**
     * Represents a platform subproject.
     *
     * Properties:
     * - **prefix** — [PLATFORM_PREFIX]
     * - **baseDir** — [PLATFORM_DIR]
     */
    PLATFORM(PLATFORM_PREFIX, PLATFORM_DIR)

    //</editor-fold>
}

/**
 * Represents a subproject in a Gradle build.
 * Defines core properties and helper methods for interacting with it.
 *
 * @property projectName Full project name with prefix.
 * @property projectPath Gradle path to the project.
 * @property projectDir  Project directory relative to the root.
 * @property buildDir    Build directory for the project.
 */
interface SubProject {

    /** Full project name including prefix. */
    val projectName: String
    /** Gradle path to the project (e.g., `:SuggestLib-common`). */
    val projectPath: String
    /** Project directory relative to the root project. Defaults to [projectName]. */
    val projectDir: String
        get() = projectName
    /** Path to the project's build directory. */
    val buildDir: String
        get() = "build/$projectDir"

    /**
     * Retrieves this subproject as a [Project] instance.
     *
     * @param root The root Gradle project.
     * @return The corresponding Gradle [Project] instance.
     */
    fun asProject(root: Project): Project =
        root.project(projectPath)

    /**
     * Checks whether this subproject exists in the given root project.
     *
     * @param root The root Gradle project.
     * @return `true` if the subproject exists, otherwise `false`.
     */
    fun exists(root: Project): Boolean =
        root.findProject(projectPath) != null
}

/**
 * Base class for all SuggestionsBot subprojects.
 *
 * Automatically constructs:
 * - **name** — full Gradle project name based on [ProjectType] and short name.
 * - **path** — Gradle path to the project.
 * - **directory** — location relative to the root.
 *
 * @param type The type of the project (LIB or PLATFORM).
 * @param name Short project name (without prefixes or directories).
 *
 * @see ProjectType
 * @see SubProject
 */
sealed class SubProjects(
    type: ProjectType,
    name: String
) : SubProject {

    /** Full Gradle project name. */
    final override val projectName = "${type.prefix}-${name.lowercase()}"
    /** Gradle path to the project. */
    final override val projectPath = ":$projectName"
    /** Path to the project's directory relative to the root. */
    final override val projectDir = "${type.baseDir}/$projectName"

    //<editor-fold desc="Subprojects" defaultstate="collapsed">

    /** Common library module with shared components. */
    object Common : SubProjects(ProjectType.LIB,      "common")
    /** Platform module for the Telegram bot. */
    object Bot    : SubProjects(ProjectType.PLATFORM, "bot")
    /** Platform module for database-related logic. */
    object DB     : SubProjects(ProjectType.PLATFORM, "db")

    //</editor-fold>

    companion object {
        /**
         * Finds a subproject by its name.
         *
         * @param name The full project name (e.g., `SuggestLib-common`).
         * @return The matching [SubProject], or `null` if not found.
         */
        fun fromName(name: String): SubProject? =
            listOf(
                /* Libraries */
                Common,
                /* Platforms */
                Bot, DB
            ).find { it.projectName == name }
    }
}
