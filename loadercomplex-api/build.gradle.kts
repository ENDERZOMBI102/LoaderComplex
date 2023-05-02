@file:Suppress("UnstableApiUsage")
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
	`maven-publish`
}

version = project.ext["api"] as String

dependencies {
	api( libs.slf4j )
	api( libs.jankson )
	api( libs.brigadier )
	api( libs.eventsystem )
}

java.withJavadocJar()
java.withSourcesJar()
artifacts.jarz( tasks.jar )

tasks.withType<JavaCompile> {
	sourceCompatibility = "8"
	options.encoding = "UTF-8"
	options.release.set(8)
}

tasks.withType<ProcessResources> {
	// set the api version
	filesMatching( "/api-version" ) {
		expand( "version" to "$version+${now().format( ofPattern("ddMMyyyy") )}" )
	}
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			group = "com.enderzombi102"
			from( components["java"] )
		}
	}

	repositories {
		mavenLocal()
		maven {
			name = "Repsy"
			credentials( PasswordCredentials::class )
			url = uri( "https://repsy.io/mvn/enderzombi102/mc" )
		}
	}
}
