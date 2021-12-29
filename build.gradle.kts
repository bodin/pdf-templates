plugins {
    kotlin("jvm") version "1.4.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox
    implementation("org.apache.pdfbox:pdfbox:2.0.25")

    // https://mvnrepository.com/artifact/com.github.dhorions/boxable
    implementation("com.github.dhorions:boxable:1.6")

    implementation("com.github.jknack:handlebars:4.3.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}