plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.panda-lang.org/releases")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
    implementation("org.mongodb:mongo-java-driver:3.12.14")

    val lombokVersion = "1.18.30"
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    val liteCommandsVersion = "3.4.3"
    implementation("dev.rollczi:litecommands-bukkit:${liteCommandsVersion}")
    implementation("dev.rollczi:litecommands-adventure:${liteCommandsVersion}")

    val okaeriConfigsVersion = "5.0.3"
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:${okaeriConfigsVersion}")
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:${okaeriConfigsVersion}")

    compileOnly("me.clip:placeholderapi:2.11.6")
}

bukkit {
    main = "pl.chudziudgi.paymc.Main"
    name = "paymc-ffa"
    version = "1.0-SNAPSHOT"
    apiVersion = "1.18"
    authors = listOf("Chudziudgi")
    depend = listOf("ProtocolLib")
    prefix = "paymc-ffa"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
