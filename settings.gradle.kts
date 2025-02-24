rootProject.name = "Moodly"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":core:database")
include(":core:ui")
include(":feature:welcome:ui")
include(":feature:mood:ui")
include(":feature:mood:domain")
include(":feature:mood:data")
include(":feature:onboarding:ui")
include(":feature:sleep-quality:data")
include(":feature:sleep-quality:ui")
include(":feature:sleep-quality:domain")
include(":feature:stress-level:data")
include(":feature:stress-level:domain")
include(":feature:stress-level:ui")
include(":feature:user:data")
include(":feature:user:domain")
include(":feature:user:ui")
include(":feature:home:ui")
include(":feature:self-journal:data")
include(":feature:self-journal:domain")
include(":feature:self-journal:ui")
include(":feature:freud-score:domain")
include(":feature:freud-score:ui")
include(":core:di")
include(":core:navigation")
include(":shared-resources")
include(":feature:splash:ui")
include(":feature:auth")
include(":feature:auth:ui")
include(":core:permission")
include(":core:storage:data")
include(":core:storage:domain")
include(":core:test")
include(":feature:security")
include(":feature:security:data")
include(":feature:security:domain")
include(":feature:security:ui")
include(":core:datastore")
include(":core:domain")
include(":core:cryptography:data")
include(":core:cryptography:domain")
include(":feature:preferences:data")
include(":feature:preferences:domain")
include(":feature:preferences:ui")
