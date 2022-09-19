@file:Suppress("UnstableApiUsage")
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
	id("fabric-loom") version "1.0-SNAPSHOT" apply false // no need to apply here
	id( "com.github.johnrengelman.shadow") version "7.1.2"
	java
	idea
}

val shade = configurations.create("shade")

allprojects {
	repositories {
		mavenLocal()
		mavenCentral()
		maven( url = "https://libraries.minecraft.net" )
		maven( url = "https://repsy.io/mvn/enderzombi102/mc" )
	}
}

subprojects {
	apply( plugin = "java" )
	if ( hasProperty("loom") ) {
		apply( plugin = property("loom") as String )
		withGroovyBuilder {
			"loom" {
				"runConfigs" {
					"client" {
						"runDir"( rootProject.file("run").absolutePath )
					}
					"server" {
						"runDir"( rootProject.file("run").absolutePath )
					}
				}
//				setProperty( "runtimeOnlyLog4j", true )
			}
		}
	}

	group = "com.enderzombi102.loadercomplex"
	version = rootProject.version

	configurations.create("jarz")

	dependencies {
		implementation( rootProject.libs.annotations )
		if ( name != "loadercomplex-common" ) {
			implementation( project( ":loadercomplex-common" ) ) {
				isTransitive = false
			}
			compileOnly( rootProject.libs.brigadier )
			implementation( rootProject.libs.slf4j )
			implementation( rootProject.libs.eventsystem )
			implementation( rootProject.libs.enderlib )
		}
	}
}

val subProjects: List<String> = rootProject.subprojects
	.stream()
	.map( Project::getName )
	.toList()

dependencies {
	for ( proj in subProjects ) {
		shade( project( path=proj, configuration="jarz" ) ) {
			isTransitive = false
		}
	}

	shade( libs.bundles.shade )
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>() {
    configurations = listOf( shade )
	archiveClassifier.set("")

	from( "LICENSE" ) {
		rename { "${it}_$archiveBaseName" }
	}

	val date = LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") )
	manifest.attributes.putAll(
		mapOf(
			Pair( "Specification-Title"     , "LoaderComplex" ),
			Pair( "Specification-Vendor"    , "Aurora Inhabitants" ),
			Pair( "Specification-Version"   , libs.versions.api ), // bundled api version
			Pair( "Implementation-Title"    , project.name ),
			Pair( "Implementation-Version"  , archiveVersion ), // mod version
			Pair( "Implementation-Vendor"   , "Aurora Inhabitants" ),
			Pair( "Implementation-Timestamp", date ), // build date
//			Pair( "FMLCorePlugin"           , "com.enderzombi102.loadercomplex.forge12.LoaderComplexCoremod" )
		)
	)
}

artifacts {
	archives( tasks["shadowJar"] )
}
