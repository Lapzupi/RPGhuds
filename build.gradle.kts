plugins {
    id("java")
    alias(libs.plugins.plugin.yml.bukkit)
    alias(libs.plugins.shadow)
}

group = "dev.lone"
version = "1.3.4"


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
    apiVersion = "1.20"
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

        relocate("co.aikar.commands", "dev.lone.rpghuds.acf")
        relocate("co.aikar.locales", "dev.lone.rpghuds.locales")
    }
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}