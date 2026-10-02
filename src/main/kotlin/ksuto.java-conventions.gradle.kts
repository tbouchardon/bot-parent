// Conventions Java communes : toolchain, encodage, Lombok, JUnit (Logback pour les logs des tests)

plugins {
    java
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
fun lib(alias: String) = libs.findLibrary(alias).get()

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.findVersion("java").get().requiredVersion)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:deprecation")
}

dependencies {
    compileOnly(lib("lombok"))
    annotationProcessor(lib("lombok"))
    testCompileOnly(lib("lombok"))
    testAnnotationProcessor(lib("lombok"))

    testImplementation(platform(lib("junit-bom")))
    testImplementation(lib("junit-jupiter"))
    testRuntimeOnly(lib("junit-platform-launcher"))
    testRuntimeOnly(lib("logback-classic"))
}

tasks.test {
    useJUnitPlatform()
}
