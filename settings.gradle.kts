pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "LunaChat"

include(":core")
include(":bukkit")
include(":velocity")
include(":bungee")

include(":discord")

include(":bundle")