plugins {
    java
    id("com.gradleup.shadow").version("9.0.0")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":bukkit"))
    implementation(project(":bungee"))
    implementation(project(":velocity"))
}

tasks {
    shadowJar {
        archiveBaseName = rootProject.name

        exclude("META-INF/LICENSE")

        relocate("org.bstats", "com.github.ucchyocean.lc3.org.bstats")
        relocate("org.apache.commons", "com.github.ucchyocean.lc3.org.apache.commons")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}