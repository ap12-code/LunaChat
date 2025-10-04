plugins {
    java
    id("xyz.jpenilla.run-velocity").version("2.3.1")
}

dependencies {
    implementation(project(":core"))

    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")

    implementation("io.github.4drian3d:signedvelocity-minestom:1.3.0")
}

tasks {
  runVelocity {
    velocityVersion("3.4.0-SNAPSHOT")
  }
}
