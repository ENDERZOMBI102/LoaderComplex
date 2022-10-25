@file:Suppress("UnstableApiUsage")
import net.minecraftforge.gradle.common.util.RunConfig

plugins {
	id("net.minecraftforge.gradle") version "5.1.0"
}

minecraft {
	mappings( "snapshot", "20171003-1.12" )

	runs {
		val runConfig: RunConfig.() -> Unit = {
			workingDirectory( rootProject.file("run") )

			property( "forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP" )
			property( "forge.logging.console.level", "debug" )

			mods.create("loadercomplex") {
				source( sourceSets.main.get() )
			}
		}
		create( "server", runConfig )
		create( "client", runConfig )
	}
}

dependencies {
	minecraft( "net.minecraftforge:forge:1.12.2-14.23.5.2854" )
}

tasks.withType<ProcessResources> {
	inputs.property( "version", rootProject.version )
	inputs.property( "description", rootProject.description )
	inputs.property( "repo_url", rootProject.ext["repo_url"] )

	filesMatching("mcmod.info") {
		expand(
			Pair( "version", rootProject.version ),
			Pair( "description", rootProject.description ),
			Pair( "repo_url", rootProject.ext["repo_url"] )
		)
	}
}

tasks.withType<Jar> {
	finalizedBy( "reobfJar" )
}

artifacts {
	jarz( file( "$buildDir/reobfJar/output.jar" ) )
}
