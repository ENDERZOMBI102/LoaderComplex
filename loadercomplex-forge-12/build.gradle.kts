@file:Suppress("UnstableApiUsage")
plugins {
	id("net.minecraftforge.gradle") version "5.1.+"
}

minecraft {
	mappings( "snapshot", "20171003-1.12" )

	runs {
		create("client") {
			workingDirectory( rootProject.file("run") )

			property( "forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP" )
			property( "forge.logging.console.level", "debug" )

			mods {
				create("loaderxomplex") {
					source( sourceSets.main.get() )
				}
			}
		}

		create("server") {
			workingDirectory( rootProject.file("run") )

			property( "forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP" )
			property( "forge.logging.console.level", "debug" )

			mods {
				create("loaderxomplex") {
					source( sourceSets.main.get() )
				}
			}
		}
	}
}

dependencies {
	minecraft( "net.minecraftforge:forge:1.12.2-14.23.5.2854" )

	// Real mod deobf dependency examples - these get remapped to your current mappings
	// compileOnly fg.deobf("mezz.jei:jei-${mc_version}:${jei_version}:api") // Adds JEI API as a compile dependency
}

tasks.withType<ProcessResources>() {
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

tasks.withType<Jar>() {
	finalizedBy( "reobfJar" )
}

artifacts {
	jarz( file( "$buildDir/reobfJar/output.jar" ) )
}
