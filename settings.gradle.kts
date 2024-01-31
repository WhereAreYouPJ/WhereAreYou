pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://naver.jfrog.io/artifactory/maven/")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://naver.jfrog.io/artifactory/maven/")
        }
    }
}
rootProject.name = "WhereAreYou"
include(":app")
include(":data")
include(":domain")
