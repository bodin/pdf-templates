import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    `java-library`
    `maven-publish`
    `signing`
}

group = "io.github.bodin"

repositories {
    mavenCentral()
}

dependencies {
    val handlebars = "com.github.jknack:handlebars:4.3.0"
    val freemarker = "org.freemarker:freemarker:2.3.31"

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("com.github.librepdf:openpdf:1.3.27")

    compileOnly(handlebars)
    compileOnly(freemarker)

    testImplementation(kotlin("test"))
    testImplementation(handlebars)
    testImplementation(freemarker)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("pdf-templates")
                description.set("A java library for generating PDF documents from an XML template.")
                url.set("https://github.com/bodin/pdf-templates")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://github.com/bodin/pdf-templates/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("bodin")
                    }
                }
                scm {
                    url.set("https://github.com/bodin/pdf-templates")
                }
            }
        }
    }

    repositories {
        maven {
            name="sonatype"
            val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials(PasswordCredentials::class)
        }
    }
}

java {
    //https://youtrack.jetbrains.com/issue/KT-45335
    targetCompatibility=JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

signing{
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}


tasks {
    wrapper {
        gradleVersion = "7.3.3"
    }
    javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    test {
        useJUnitPlatform()
    }
}