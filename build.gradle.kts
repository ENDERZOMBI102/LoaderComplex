@file:Suppress("UnstableApiUsage", "LocalVariableName")
import net.fabricmc.loom.LoomGradleExtension
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
		maven( url = "https://maven.terraformersmc.com/releases" )
	}
}

val api = project( ":loadercomplex-api" )
subprojects {
	apply( plugin = "java" )

	group = "com.enderzombi102.loadercomplex"
	version = rootProject.version

	configurations.create("jarz")

	if ( hasProperty("loom") ) {
		apply( plugin = property("loom") as String )

		( extensions["loom"] as LoomGradleExtension ).apply {
			runConfigs {
				named("client") {
					runDir = rootProject.file("run").relativeTo(projectDir).path
				}
				named("server") {
					runDir = rootProject.file("run").relativeTo(projectDir).path
				}
			}
			runtimeOnlyLog4j.set(true)
			shareRemapCaches.set(true)
		}

		artifacts.add( "jarz", tasks["remapJar"] )
	}

	dependencies {
		implementation( rootProject.libs.annotations )
		if ( name != api.name ) {
			implementation( project( api.path ) ) {
				isTransitive = false
			}
			compileOnly( rootProject.libs.brigadier )
			implementation( rootProject.libs.slf4j )
			implementation( rootProject.libs.eventsystem )
			implementation( rootProject.libs.enderlib )
		}
	}

	tasks.withType<JavaCompile> {
		sourceCompatibility = project.ext["java_version"] as String
		options.encoding = "UTF-8"
		options.release.set( Integer.valueOf( sourceCompatibility ) )
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

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    configurations = listOf( shade )
	archiveClassifier.set("")

	from( "LICENSE" ) {
		rename { "${it}_$archiveBaseName" }
	}

	manifest.attributes(
		"Specification-Title"      to "LoaderComplex",
		"Specification-Vendor"     to "Aurora Inhabitants",
		"Specification-Version"    to libs.versions.api, // bundled api version
		"Implementation-Title"     to project.name,
		"Implementation-Version"   to archiveVersion, // mod version
		"Implementation-Vendor"    to "Aurora Inhabitants",
		"Implementation-Timestamp" to now().format( ofPattern("dd-MM-yyyy'T'HH:mm:ss") ), // build date
	)
}

artifacts {
	archives( tasks["shadowJar"] )
}
