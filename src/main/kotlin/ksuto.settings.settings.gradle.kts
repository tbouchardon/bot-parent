// Settings communs à tous les projets ksuto :
//   pluginManagement { includeBuild("../Bot Parent") }
//   plugins { id("ksuto.settings") }

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files(settingsDir.resolve("../Bot Parent/gradle/libs.versions.toml")))
        }
    }
}
