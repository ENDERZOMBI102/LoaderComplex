@file:Suppress("UnstableApiUsage", "LocalVariableName")
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import xyz.wagyourtail.unimined.api.UniminedExtension
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
	// plugins for the subprojects, no need to apply here
	id( "org.jetbrains.kotlin.jvm" ) version "1.8.0" apply false
	id( "xyz.wagyourtail.unimined" ) apply false
	// root project plugins
	id( "com.github.johnrengelman.shadow") version "7.1.2"
	java
	idea
}

val shade = configurations.create( "shade" )

allprojects {
	repositories {
		mavenLocal()
		mavenCentral()
		maven( url = "https://libraries.minecraft.net" )
		maven( url = "https://repsy.io/mvn/enderzombi102/mc" ) {
			content { includeGroup( "com.enderzombi102" ) }
		}
		maven( url = "https://repo.sleeping.town" ) {
			content { includeGroup( "com.unascribed" ) }
		}
	}
}



val api = project( ":loadercomplex-api" )
val common = project( ":loadercomplex-common" )
subprojects {
	apply( plugin = "java" )
	apply( plugin = "org.jetbrains.kotlin.jvm" )

	group = "com.enderzombi102.loadercomplex"

	configurations.create( "jarz" )

	if ( hasProperty( "minecraftVersion" ) ) {
		logger.lifecycle( "Found subproject: `$path` ( minecraft ${ext["minecraftVersion"]} )" )

		// setup unimined
		apply( plugin = "xyz.wagyourtail.unimined" )
		val unimined = ( extensions[ "unimined" ] as UniminedExtension )
		unimined.useGlobalCache = false

		configurations.create( "modCompileOnly" ) {
			configurations["compileOnly"].extendsFrom( this )
			unimined.minecraft( lateApply=true ) { mods.remap( this@create ) }
		}

		configurations.create( "modRuntimeOnly" ) {
			configurations["runtimeOnly"].extendsFrom( this )
			unimined.minecraft( lateApply=true ) { mods.remap( this@create ) }
		}

		afterEvaluate {
			artifacts.add( "jarz", tasks[ "remapJar" ] )
		}
	} else
		logger.lifecycle( "Found subproject: `$path`" )

	dependencies {
		implementation( rootProject.libs.annotations )
		compileOnly( kotlin( "stdlib-jdk8" ) )
		if ( name != api.name && name != common.name ) {
			implementation( project( common.path ) )
			compileOnly( rootProject.libs.brigadier )
		}
	}

	tasks.withType<JavaCompile> {
		val javaVersion: String by project
		sourceCompatibility = javaVersion
		options.encoding = "UTF-8"
		options.release.set( javaVersion.toInt() )
	}

	tasks.withType<KotlinCompile> {
		compilerOptions.jvmTarget.set( JvmTarget.JVM_1_8 )
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
	archiveClassifier.set( "" )

	from( "LICENSE" ) {
		rename { "${it}_$archiveBaseName" }
	}

	manifest.attributes(
		"Specification-Title"      to "LoaderComplex",
		"Specification-Vendor"     to "Aurora Inhabitants",
		"Specification-Version"    to api.version,    // bundled api version
		"Implementation-Title"     to project.name,
		"Implementation-Version"   to archiveVersion, // mod version
		"Implementation-Vendor"    to "Aurora Inhabitants",
		"Implementation-Timestamp" to now().format( ofPattern( "dd-MM-yyyy'T'HH:mm:ss" ) ), // build date
	)
}

artifacts {
	archives( tasks["shadowJar"] )
}
