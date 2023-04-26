rootProject.name = "lapzupi-huds"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("paper-api", "io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
            library("itemsadder-api", "com.github.LoneDev6:api-itemsadder:3.4.1-r4")
            library("vault-api", "com.github.MilkBowl:VaultAPI:1.7.1")
            library("placeholder-api", "me.clip:placeholderapi:2.11.3")
            library("commands-paper", "co.aikar:acf-paper:0.5.1-SNAPSHOT")
            library("commons-io", "commons-io:commons-io:2.11.0")
            library("lapzupi-config","com.github.Lapzupi:LapzupiConfig:1.1.1")
            library("lapzupi-files","com.github.Lapzupi:LapzupiFiles:1c7a837b53")
            library("configurate-yaml", "org.spongepowered:configurate-yaml:4.1.2")
        }
    }
}