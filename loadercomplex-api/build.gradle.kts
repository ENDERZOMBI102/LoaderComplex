@file:Suppress("UnstableApiUsage")
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern
plugins {
	`maven-publish`
}

dependencies {
	api( libs.slf4j )
	api( libs.jankson )
	api( libs.brigadier )
	api( libs.annotations )
}

java.withJavadocJar()
java.withSourcesJar()
artifacts.jarz( tasks.jar )

tasks.withType<ProcessResources> {
	// set the api version
	filesMatching( "/api-version" ) {
		expand( "version" to "$version+${now().format( ofPattern("ddMMyyyy") )}" )
	}
}
tasks.withType<Javadoc> {
	title = "LoaderComplex $version API"
	(options as StandardJavadocDocletOptions).addStringOption( "tag" ).value = "implNote:a:Implementation note:"
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
