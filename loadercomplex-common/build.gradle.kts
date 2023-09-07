@file:Suppress("UnstableApiUsage")
plugins {
	`maven-publish`
}

dependencies {
	api( project( ":loadercomplex-api" ) )
	compileOnly( libs.enderlib )
	compileOnly( libs.flexver )
}

artifacts.jarz( tasks.jar )

tasks.withType<ProcessResources> {
	val configuration = listOf( "jdk8", "common" )
		.asSequence()
		.map { dependencies.create( dependencies.kotlin( "stdlib-$it", kotlin.coreLibrariesVersion ) ) }
		.toList()
		.toTypedArray()
		.run { configurations.detachedConfiguration( *this ) }

	// resolve configuration and add the jars
	from( configuration.files ) {
		into( "jars/" )
	}
	// list the jars in a file, so there is no hardcoding
	filesMatching( "/jars/list" ) {
		expand( "files" to configuration.files.joinToString("\n") { it.name } )
	}
}
