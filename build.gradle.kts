@file:Suppress("UnstableApiUsage", "LocalVariableName")
import net.fabricmc.loom.LoomGradleExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
	// plugins for the subprojects, no need to apply here
	id( "org.jetbrains.kotlin.jvm" ) version "1.8.0" apply false
	id( "dev.architectury.loom" ) version "1.+" apply false
	id( "org.quiltmc.loom" ) version "1.+" apply false
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

val api = project( ":loadercomplex-api" )
val common = project( ":loadercomplex-common" )
subprojects {
	apply( plugin = "java" )
	apply( plugin = "org.jetbrains.kotlin.jvm" )

	group = "com.enderzombi102.loadercomplex"

	configurations.create("jarz")

	if ( hasProperty("loom") ) {
		apply( plugin = property("loom") as String )

		repositories.maven( url = "https://maven.terraformersmc.com/releases" )

		( extensions["loom"] as LoomGradleExtension ).apply {
			runConfigs {
				named("client") {
					runDir = rootProject.file("run").relativeTo( projectDir ).path
				}
				named("server") {
					runDir = rootProject.file("run").relativeTo( projectDir ).path
				}
			}
			runtimeOnlyLog4j.set(true)
		}

		artifacts.add( "jarz", tasks["remapJar"] )
	}

	dependencies {
		implementation( rootProject.libs.annotations )
		compileOnly( kotlin( "stdlib-jdk8" ) )
		if ( name != api.name && name != common.name ) {
			implementation( project( common.path ) )
			compileOnly( rootProject.libs.brigadier )
		}
	}

	tasks.withType<JavaCompile> {
		val java_version: String by project
		sourceCompatibility = java_version
		options.encoding = "UTF-8"
		options.release.set( java_version.toInt() )
	}

	tasks.withType<KotlinCompile> {
		compilerOptions.jvmTarget.set( JVM_1_8 )
	}
}

val subProjects: List<String> = rootProject.subprojects
	.asSequence()
	.filter { it.parent == rootProject }
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
		"Specification-Version"    to project.ext["api"], // bundled api version
		"Implementation-Title"     to project.name,
		"Implementation-Version"   to archiveVersion, // mod version
		"Implementation-Vendor"    to "Aurora Inhabitants",
		"Implementation-Timestamp" to now().format( ofPattern("dd-MM-yyyy'T'HH:mm:ss") ), // build date
	)
}

artifacts {
	archives( tasks["shadowJar"] )
}
