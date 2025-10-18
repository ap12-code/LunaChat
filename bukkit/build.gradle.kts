plugins {
    java
}

dependencies {
    implementation(project(":core"))

    compileOnly("io.papermc:paperlib:1.0.8")
    compileOnly("io.papermc.paper:paper-api:1.21.9-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("org.bstats:bstats-bukkit:3.1.0")

    compileOnly("com.github.MilkBowl:VaultAPI:1.7") {
        exclude("org.bukkit")
    }

    compileOnly("org.mvplugins.multiverse.core:multiverse-core:5.1.1") {
        exclude("org.bukkit")
        exclude("com.github.MilkBowl")
        exclude("org.bstats")
    }

    compileOnly("org.dynmap:dynmap-api:2.0") {
        exclude("org.bukkit")
    }

    compileOnly("com.gmail.nossr50.mcMMO:mcMMO:2.2.031") {
        exclude("org.apache.maven.scm")
        exclude("org.spigotmc")
        exclude("org.bstats")
        exclude("org.bukkit")
        exclude("com.google")
        exclude("com.google.guava")
        exclude("com.sk89q.worldguard")
        exclude("net.kyori")
        exclude("com.comphenix.protocol")
    }
}

tasks.processResources {
    filesMatching("**/plugin.yml") {
        expand("version" to project.version)
    }
}