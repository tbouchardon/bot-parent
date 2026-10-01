plugins {
    `kotlin-dsl`
}

dependencies {
    // Plugin de settings appliqué par ksuto.settings (téléchargement automatique des JDK)
    implementation("org.gradle.toolchains.foojay-resolver-convention:org.gradle.toolchains.foojay-resolver-convention.gradle.plugin:${libs.versions.foojay.get()}")
}
