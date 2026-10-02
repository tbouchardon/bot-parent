# Bot Parent : logique de build partagée

Conventions Gradle et catalogue de versions communs à tous les projets ksuto
(Commons, Logger, Bot Generator, Bot Peripherals, ClockWork).

## Organisation attendue

Les dépôts sont clonés côte à côte, avec ces noms de dossiers :

```
00_Perso/
├── Bot Parent/        ← ce dépôt
├── Commons/           fr.ksuto:commons
├── Logger/            fr.ksuto:logger
├── Bot Generator/     fr.ksuto.bot:generator
├── Bot Peripherals/   fr.ksuto.bot:peripherals
└── ClockWork/         application
```

Chaque projet inclut ses dépendances ksuto **depuis leurs sources** (`includeBuild("../…")`) :
une modification dans `Bot Peripherals` est visible immédiatement dans ClockWork, sans `install`.

## Contenu

| Fichier | Rôle |
|---|---|
| `gradle/libs.versions.toml` | toutes les versions (Java, Lombok, Guice…) |
| `ksuto.settings` | plugin de settings : dépôts, catalogue `libs`, téléchargement automatique des JDK (foojay) |
| `ksuto.java-conventions` | Java 25 (toolchain), UTF-8, Lombok, JUnit |
| `ksuto.java-library` | bibliothèque + `publishToMavenLocal` |
| `ksuto.java-application` | application (`mainClass` à définir) |
| `ksuto.picture-enums` | génère les enums depuis `src/main/resources/picturesToEnum/<dossier>/` |

Un projet ksuto se déclare ainsi :

```kotlin
// settings.gradle.kts
pluginManagement { includeBuild("../Bot Parent") }
plugins { id("ksuto.settings") }
rootProject.name = "logger"
includeBuild("../Commons")
```

```kotlin
// build.gradle.kts
plugins { id("ksuto.java-library") }
dependencies { api(libs.ksuto.commons) }
```

## Logs

API unique : **SLF4J** (`private static final Logger logger = LoggerFactory.getLogger(MaClasse.class);`).
Les bibliothèques ne dépendent que de `slf4j-api` ; les applications ajoutent `libs.ksuto.logger` (Logback, fenêtre Swing,
aspect Guice) et un `src/main/resources/logback.xml` :

```xml
<configuration>
    <include resource="fr/ksuto/logger/logback-ksuto.xml"/>
    <!-- <include resource="fr/ksuto/logger/logback-ksuto-swing.xml"/> fenêtre de log, optionnelle -->
    <logger name="fr.ksuto.clockwork" level="DEBUG"/>
    <root level="WARN"><appender-ref ref="CONSOLE"/></root>
</configuration>
```

## Thème Swing

`Theme.apply()` (Commons) applique FlatLaf avant la création des fenêtres ; le thème se choisit au lancement avec
`-Dksuto.theme=dark|light|intellij|darcula|none` (dark par défaut, none = thème Swing d'origine).

## Lancer un build

`./gradlew build` (Windows : `gradlew.bat build`). Gradle 9 doit être lancé avec un JDK ≥ 17 ;
le JDK 25 de compilation est trouvé ou téléchargé automatiquement.
Derrière le proxy du ministère, activer les lignes `systemProp.http(s).proxy*` de `~/.gradle/gradle.properties`.
