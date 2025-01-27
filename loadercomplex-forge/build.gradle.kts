@file:Suppress("HasPlatformType")
import lc.setupRuns
plugins {
	id( "xyz.wagyourtail.unimined" )
}

repositories {
	unimined.ornitheMaven()
	maven( url="https://maven.terraformersmc.com" )
}

val ver122 by sourceSets.creating { }
val ver182 by sourceSets.creating { }

unimined.minecraft( ver122 ) {
	combineWith( sourceSets["main"] )
	version( "1.12.2" )

	mappings {
		mcp( "snapshot", "20171003-1.12" )

//		mapping( "net.ornithemc:feather-gen2:${minecraft.version}+build.4:v2", "feather" ) {
//			outputs( "feather", true ) { listOf("intermediary") }
//			mapNamespace( "named", "feather" )
//			sourceNamespace( "intermediary" )
//			renest()
//		}
//		mapping("net.ornithemc:calamus-intermediary-gen2:${minecraft.version}:v2", "intermediary") {
//			outputs("intermediary", false) { listOf("official") }
//		}
//
//		devFallbackNamespace( "intermediary" )
	}

	runs.setupRuns()
	runs.all { javaVersion = JavaVersion.VERSION_1_8 }

	minecraftForge { loader( "14.23.5.2854" ) }
}

unimined.minecraft( ver182 ) {
	combineWith( sourceSets["main"] )
	version( "1.18.2" )

	mappings {
		yarn( 3 )
		intermediary()

		devFallbackNamespace( "intermediary" )
	}

	runs.setupRuns()

	minecraftForge { loader( "40.1.80" ) }
}

dependencies {
	implementation( project( ":loadercomplex-common" ) )

	"ver122Implementation"( libs.annotations )
	"ver122Implementation"( project( ":loadercomplex-common" ) )
//	"ver122CompileOnly"( libs.brigadier )

	"ver182Implementation"( libs.annotations )
	"ver182Implementation"( project( ":loadercomplex-common" ) )
//	"ver182CompileOnly"( libs.brigadier )
}

tasks.withType<ProcessResources> {
	inputs.property( "version", rootProject.version )
	inputs.property( "description", rootProject.description )
	inputs.property( "repo_url", rootProject.ext["repo_url"] )

	filesMatching( "META-INF/mods.toml" ) {
		expand(
			"version" to rootProject.version,
			"description" to rootProject.description!!.replace("\"", "\\\""),
			"repo_url" to rootProject.ext["repo_url"],
			"forge_version" to "40.1.80",
		)
	}
	filesMatching( "mcmod.info" ) {
		expand(
			"version" to rootProject.version,
			"description" to rootProject.description!!.replace("\"", "\\\""),
			"repo_url" to rootProject.ext["repo_url"]
		)
	}
}

//afterEvaluate {
//	artifacts.add( "jarz", tasks[ "remapJar" ] )
//}
