pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }
}

rootProject.name = "LunaChat"

include(":core")
include(":bukkit")
include(":velocity")
include(":bungee")
include(":fabric")

include(":bundle")