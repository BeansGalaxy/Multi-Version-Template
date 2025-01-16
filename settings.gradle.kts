pluginManagement.repositories {
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.neoforged.net/releases/")
    gradlePluginPortal()
}

plugins {
    `gradle-enterprise`
}

gradleEnterprise.buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

include(
    "modules:1.21",
    "modules:1.21:common",
    "modules:1.21:fabric",
    "modules:1.21:neoforge"
)

include(
    "modules:1.20.1",
    "modules:1.20.1:common",
    "modules:1.20.1:fabric",
    "modules:1.20.1:forge"
)

rootProject.name = "Multi-Version-Template"
