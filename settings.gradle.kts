pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
//        maven {
//            url = uri("https://naver.jfrog.io/artifactory/maven/")
//            url = uri("https://repository.map.naver.com/archive/maven")
//        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://naver.jfrog.io/artifactory/maven/") }
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven { url = uri("https://repository.map.naver.com/archive/maven") }
    }
}
rootProject.name = "WhereAreYou"
include(
    ":app",
    ":data",
    ":domain"
)
//include(":data")
//include(":domain")
