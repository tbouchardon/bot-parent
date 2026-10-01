// Génère des enums à partir des images de src/main/resources/picturesToEnum/<dossier>/
// (remplace l'exécution de PicturesEnumsGenerator par exec-maven-plugin)

plugins {
    java
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

val pictureEnumsGenerator by configurations.creating

dependencies {
    pictureEnumsGenerator(libs.findLibrary("ksuto-generator").get())
}

val generatePictureEnums by tasks.registering(JavaExec::class) {
    group = "build"
    description = "Génère les enums d'images depuis src/main/resources/picturesToEnum"

    val input = layout.projectDirectory.dir("src/main/resources/picturesToEnum")
    val output = layout.buildDirectory.dir("generated/sources/picture-enums")

    classpath = pictureEnumsGenerator
    mainClass = "fr.ksuto.bot.common.PicturesEnumsGenerator"
    inputs.files(fileTree(input)).withPropertyName("pictures")
    outputs.dir(output).withPropertyName("enums")
    argumentProviders.add(CommandLineArgumentProvider {
        listOf(input.asFile.absolutePath, output.get().asFile.absolutePath)
    })
    doFirst { delete(output) }
}

sourceSets.main {
    java.srcDir(generatePictureEnums)
}
