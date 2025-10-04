plugins {
    java
}

dependencies {
    implementation(project(":core"))

    compileOnly("net.md-5:bungeecord-api:1.21-R0.2")
    compileOnly("net.md-5:bungeecord-chat:1.21-R0.2")

    compileOnly("net.kyori:adventure-platform-bungeecord:4.3.4")
    compileOnly("org.bstats:bstats-bungeecord:3.1.0")
    compileOnly("net.alpenblock:BungeePerms:4.0-dev-114")
}