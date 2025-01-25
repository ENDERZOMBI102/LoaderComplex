@file:Suppress("UnstableApiUsage")

import java.util.jar.JarFile


plugins {
	`maven-publish`
}

dependencies {
	implementation( libs.annotations )
	compileOnly( libs.log4j )
	api( project( ":loadercomplex-api" ) )
	runtimeOnly( "org.apache.logging.log4j:log4j-slf4j-impl:${libs.versions.log4j.get()}" )
}

artifacts.jarz( tasks.jar )


/**
 * Copy our runtime dependencies into the jar and populate the jar list.
 * the list has this format:
 */
tasks.withType<ProcessResources> {
	inputs.property( "kotlin", kotlin.coreLibrariesVersion )
	inputs.property( "log4j", libs.versions.log4j )
	inputs.property( "brigadier", libs.versions.brigadier )

	// resolve all the dependencies we might need at runtime
	val configuration = configurations.detachedConfiguration(
		dependencies.create( dependencies.kotlin( "stdlib-jdk8", kotlin.coreLibrariesVersion ) ),
		dependencies.create( dependencies.kotlin( "stdlib-common", kotlin.coreLibrariesVersion ) ),
		dependencies.create( "org.apache.logging.log4j:log4j-slf4j-impl:${libs.versions.log4j.get()}" ),
		dependencies.create( "org.apache.logging.log4j:log4j-slf4j2-impl:${libs.versions.log4j.get()}" ),
		libs.brigadier.get()
	)

	val list = buildString {
		for ( file in configuration.files ) {
			val hash = file.readBytes().contentHashCode()

			val classes = with ( JarFile( file ) ) {
				stream()
					.filter { it.name.endsWith( ".class" ) }
					.limit( 2 )
					.toList()
					.joinToString( ":" )
			}

			append( "${file.name}\t${file.length()}\t${hash}\t${classes}\n" )
		}
	}

	// resolve configuration and add the jars
	from( configuration.files ) {
		into( "jars/" )
	}
	// list the jars in a file, so there is no hardcoding
	filesMatching( "/jars/list" ) {
		expand( "files" to list )
	}
}
