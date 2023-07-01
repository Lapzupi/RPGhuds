plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

group = "dev.lone"
version = "1.3.2"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.vault.api)
    compileOnly(libs.placeholder.api)
    compileOnly(libs.itemsadder.api)

    implementation(libs.commands.paper)
    implementation(libs.lapzupi.config)
    implementation(libs.lapzupi.files)
    
    library(libs.configurate.yaml)
    library(libs.commons.io)
}

bukkit {
    name = "LapzupiHuds"
    authors = listOf("LoneDev", "sarhatabaot")
    main = "dev.lone.rpghuds.Main"
    version = project.version.toString()
    
    depend = listOf("ItemsAdder", "PlaceholderAPI")
    softDepend = listOf("Vault")
    apiVersion = "1.19"
}


tasks {
    build {
        dependsOn(shadowJar)
    }
    
    shadowJar {
        minimize()
        
        dependencies {
            exclude(dependency("net.kyori:.*:.*"))
        }
        
        archiveFileName.set("LapzupiHuds-${project.version}.jar")
        archiveClassifier.set("shadow")
    }
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}