package lc

import org.gradle.api.JavaVersion
import xyz.wagyourtail.unimined.api.runs.RunsConfig


fun RunsConfig.setupRuns() {
	arrayOf( "client", "server" ).forEach { side ->
		config( side ) {
			val version = name.substring( 3, 6 )
				.chunked( 2 )
				.joinToString( ".", "1." )
			val loader = project.name.substring( 14 )

			description = "$loader $version $side"

			javaVersion = JavaVersion.VERSION_17

			jvmArgs( "-Dlog4j.configurationFile=${project.rootProject.file("log4j2.xml").absolutePath}" )
			jvmArgs( "-Dlog4j2.configurationFile=${project.rootProject.file("log4j2.xml").absolutePath}" )
			workingDir( project.rootProject.file( "run/$side" ).absolutePath )
		}
		preLaunch( side, ":loadercomplex-test:jar" )
	}
}
