@file:Suppress("UnstableApiUsage")
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
	`maven-publish`
}

version = project.ext["api"] as String

dependencies {
	implementation( libs.slf4j )
	implementation( libs.jankson )
	implementation( libs.brigadier )
	implementation( libs.eventsystem )
	compileOnly( libs.enderlib )
}

java.withJavadocJar()
java.withSourcesJar()
artifacts.jarz( tasks.jar )

tasks.withType<JavaCompile> {
	sourceCompatibility = "8"
	options.encoding = "UTF-8"
	options.release.set(8)
}

tasks.withType<Javadoc> {
	exclude( "com/enderzombi102/loadercomplex/impl/**" )
}

tasks.withType<ProcessResources> {
	val configuration = configurations.detachedConfiguration(
		dependencies.create( dependencies.kotlin( "stdlib-jdk8", kotlin.coreLibrariesVersion ) ),
		dependencies.create( dependencies.kotlin( "stdlib-common", kotlin.coreLibrariesVersion ) )
	)
	// resolve configuration and add the jars
	from( configuration.files ) {
		into( "jars/" )
	}
	// list the jars in a file, so there is no hardcoding
	filesMatching( "/jars/list" ) {
		expand( "files" to configuration.files.joinToString("\n") { it.name })
	}
	// set the api version
	filesMatching( "/api-version" ) {
		expand(
			"version" to version,
			"timestamp" to now().format( ofPattern("dd-MM-yyyy'T'HH:mm:ss") )
		)
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
