plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.neoforged.moddev") version "2.0.74"
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

val minecraftVersion = project.properties["minecraft_version"] as String

configurations {
    create("common")
    create("shadowCommon")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentNeoForge").extendsFrom(configurations["common"])
}

loom {
    accessWidenerPath.set(project(":${project.properties["project_path"]}:common").loom.accessWidenerPath)

    // Forge Datagen Gradle config.  Remove if not using Forge datagen
    runs.create("datagen") {
        data()
        programArgs("--all", "--mod", "${project.properties["mod_id"]}")
        programArgs("--output", project(":${project.properties["project_path"]}:common").file("src/main/generated/resources").absolutePath)
        programArgs("--existing", project(":${project.properties["project_path"]}:common").file("src/main/resources").absolutePath)
    }
}

dependencies {
    neoForge("net.neoforged:neoforge:21.0.167")

    "common"(project(":${project.properties["project_path"]}:common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":${project.properties["project_path"]}:common", "transformProductionNeoForge")) { isTransitive = false }
}


tasks {
    base.archivesName.set(base.archivesName.get() + "-NeoForge")

    shadowJar {
        exclude("fabric.mod.json")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
    }

    jar.get().archiveClassifier.set("dev")

    sourcesJar {
        val commonSources = project(":${project.properties["project_path"]}:common").tasks.sourcesJar
        dependsOn(commonSources)
        from(commonSources.get().archiveFile.map { zipTree(it) })
    }
}

components {
    java.run {
        if (this is AdhocComponentWithVariants)
            withVariantsFromConfiguration(project.configurations.shadowRuntimeElements.get()) { skip() }
    }
}

publishing {
    publications.create<MavenPublication>("mavenCommon") {
        artifactId = "${project.properties["archives_base_name"]}" + "-NeoForge"
        from(components["java"])
    }

    repositories {
        mavenLocal()
        maven {
            val releasesRepoUrl = "https://example.com/releases"
            val snapshotsRepoUrl = "https://example.com/snapshots"
            url = uri(if (project.version.toString().endsWith("SNAPSHOT") || project.version.toString().startsWith("0")) snapshotsRepoUrl else releasesRepoUrl)
            name = "ExampleRepo"
            credentials {
                username = project.properties["repoLogin"]?.toString()
                password = project.properties["repoPassword"]?.toString()
            }
        }
    }
}