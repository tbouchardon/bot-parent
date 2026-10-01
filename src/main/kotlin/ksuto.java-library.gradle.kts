// Bibliothèque ksuto : conventions Java + publication (publishToMavenLocal reste possible)

plugins {
    id("ksuto.java-conventions")
    `java-library`
    `maven-publish`
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
