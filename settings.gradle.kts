pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "ComposeMockApi"
include(":app")

include(":network-core")
project(":network-core").projectDir = File(rootDir, "data/network")

include(":event-list-domain")
project(":event-list-domain").projectDir = File(rootDir, "domain/event-list")

include(":event-list-data")
project(":event-list-data").projectDir = File(rootDir, "data/event-list")

include(":event-list-presentation")
project(":event-list-presentation").projectDir = File(rootDir, "presentation/events")

include(":event-list-injector")
project(":event-list-injector").projectDir = File(rootDir, "injector/event-list")
