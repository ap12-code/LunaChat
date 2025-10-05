dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            name = "spigotmc"
            url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        }
        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }
        maven {
            name = "multiverse"
            url = uri("https://repo.onarandombox.com/multiverse-releases/")
        }
        maven {
            name = "bungeeperms"
            url = uri("https://repo.wea-ondara.net/repository/public/")
        }
        maven {
            name = "dynmap"
            url = uri("https://repo.mikeprimm.com/")
        }
        maven {
            name = "mcmmo"
            url = uri("https://nexus.neetgames.com/repository/maven-public")
        }
    }
}

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

include(":bundle")