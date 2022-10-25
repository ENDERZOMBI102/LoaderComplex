@file:Suppress("UnstableApiUsage", "LocalVariableName")
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
	// plugins for the subprojects, no need to apply here
	id( "dev.architectury.loom" ) version "0.12.+" apply false
	id( "org.quiltmc.loom" ) version "0.12.+" apply false
	// root project plugins
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

	group = "com.enderzombi102.loadercomplex"
	version = rootProject.version

	configurations.create("jarz")

	if ( hasProperty("loom") ) {
		apply( plugin = property("loom") as String )
		withGroovyBuilder {
			"loom" {
				"runConfigs" {
					"client" {
						"runDir"( rootProject.file("run").relativeTo(projectDir).path )
					}
					"server" {
						"runDir"( rootProject.file("run").relativeTo(projectDir).path )
					}
				}
//				setProperty( "runtimeOnlyLog4j", true )
			}
		}
		artifacts.add( "jarz", tasks["remapJar"] )
	}

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

	tasks.withType<JavaCompile> {
		val java_version: String by project
		sourceCompatibility = java_version
		options.encoding = "UTF-8"
		options.release.set( Integer.valueOf( java_version ) )
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

	manifest.attributes(
		( "Specification-Title"      to "LoaderComplex" ),
		( "Specification-Vendor"     to "Aurora Inhabitants" ),
		( "Specification-Version"    to libs.versions.api ), // bundled api version
		( "Implementation-Title"     to project.name ),
		( "Implementation-Version"   to archiveVersion ), // mod version
		( "Implementation-Vendor"    to "Aurora Inhabitants" ),
		( "Implementation-Timestamp" to now().format( ofPattern("dd-MM-yyyy'T'HH:mm:ss") ) ), // build date
//		( "FMLCorePlugin"            to "com.enderzombi102.loadercomplex.forge12.LoaderComplexCoremod" )
	)
}

artifacts {
	archives( tasks["shadowJar"] )
}
