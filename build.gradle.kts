@file:Suppress("UnstableApiUsage", "LocalVariableName")
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter.ofPattern

plugins {
	// plugins for the subprojects, no need to apply here
	id( "org.jetbrains.kotlin.jvm" ) version "1.8.0" apply false
	// root project plugins
	id( "com.github.johnrengelman.shadow") version "7.1.2"
	java
	idea
}

val shade = configurations.create( "shade" )

allprojects {
	apply( plugin = "xyz.wagyourtail.unimined" )

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

subprojects {
	apply( plugin = "org.jetbrains.kotlin.jvm" )

	group = "com.enderzombi102.loadercomplex"

	configurations.create( "jarz" )
	logger.lifecycle( "Found subproject: `$path`" )

	tasks.withType<JavaCompile> {
		sourceCompatibility = "8"
		targetCompatibility = "8"
		options.encoding = "UTF-8"
	}

	tasks.withType<KotlinCompile> {
		compilerOptions.jvmTarget.set( JvmTarget.JVM_1_8 )
	}
}

dependencies {
	// depend on all "type subprojects"
	rootProject.subprojects.stream()
		.filter { it.parent == rootProject }
		.map( Project::getName )
		.forEach {
			shade( project( path=it, configuration="jarz" ) ) {
				isTransitive = false
			}
		}
	shade( libs.bundles.shade )
}

val genIntellijRuns: Task by tasks.creating {
	group = "unimined_runs"
	description = "Generate IntelliJ run configurations for all subprojects and versions."

	rootProject.subprojects.forEach {
		try {
			dependsOn( it.tasks.getByName( "genIntellijRun" ).path )
		} catch ( _: UnknownTaskException ) { }
	}
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    configurations = listOf( shade )
	archiveClassifier.set( "" )

	from( "LICENSE" ) {
		rename { "${it}_$archiveBaseName" }
	}

	val api = project( ":loadercomplex-api" )
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
