@file:Suppress("HasPlatformType")
import lc.setupRuns
plugins {
	id( "xyz.wagyourtail.unimined" )
}

repositories {
	unimined.ornitheMaven()
	unimined.legacyFabricMaven()
	maven( url="https://maven.terraformersmc.com" )
}

val ver122 by sourceSets.creating { }
val ver171 by sourceSets.creating { }
val ver202 by sourceSets.creating { }

unimined.minecraft( ver122 ) {
	combineWith( sourceSets["main"] )
	version( "1.12.2" )

	@Suppress("UnstableApiUsage")
	mappings {
		mapping( "net.ornithemc:feather-gen2:${minecraft.version}+build.1:v2", "feather" ) {
			outputs( "feather", true ) { listOf("intermediary") }
			mapNamespace( "named", "feather" )
			sourceNamespace( "intermediary" )
			renest()
		}
		mapping("net.ornithemc:calamus-intermediary-gen2:${minecraft.version}:v2", "intermediary") {
			outputs("intermediary", false) { listOf("official") }
		}

		devFallbackNamespace( "intermediary" )
	}

	runs.setupRuns()

	fabric { loader( libs.versions.floader.get() ) }
}

unimined.minecraft( ver171 ) {
	combineWith( sourceSets["main"] )
	version( "1.17.1" )

	mappings {
		intermediary()
		yarn( 65 )

		devFallbackNamespace( "intermediary" )
	}

	runs.setupRuns()

	fabric { loader( libs.versions.floader.get() ) }
}

unimined.minecraft( ver202 ) {
	combineWith( sourceSets["main"] )
	version( "1.20.2" )

	mappings {
		intermediary()
		yarn( 4 )

		devFallbackNamespace( "intermediary" )
	}

	runs.setupRuns()

	fabric { loader( libs.versions.floader.get() ) }
}

dependencies {
	implementation( project( ":loadercomplex-common" ) )
	implementation( libs.floader )
	implementation( libs.mixins )

	"ver122Implementation"( libs.annotations )
	"ver122Implementation"( project( ":loadercomplex-common" ) )
//	"ver122ModImplementation"( "com.terraformersmc:modmenu:0.2.0+mc1.12.2" )
//	"ver122CompileOnly"( libs.brigadier )

	"ver171Implementation"( libs.annotations )
	"ver171Implementation"( project( ":loadercomplex-common" ) )
	"ver171ModImplementation"( unimined.fabricApi.fabric( "0.46.1+1.17" ) )
	"ver171ModImplementation"( "com.terraformersmc:modmenu:2.0.17" ) { isTransitive = false }
//	"ver171CompileOnly"( libs.brigadier )

	"ver202Implementation"( libs.annotations )
	"ver202Implementation"( project( ":loadercomplex-common" ) )
	"ver202ModImplementation"( unimined.fabricApi.fabric( "0.91.6+1.20.2" ) )
//	"ver202CompileOnly"( libs.brigadier )
}

tasks.withType<ProcessResources> {
	inputs.property( "version", rootProject.version )
	inputs.property( "description", rootProject.description )
	inputs.property( "repo_url", rootProject.ext["repo_url"] )

	filesMatching("fabric.mod.json") {
		expand(
			"version" to rootProject.version,
			"description" to rootProject.description!!.replace( "\"", "\\\"" ),
			"repo_url" to rootProject.ext["repo_url"]
		)
	}
}

//afterEvaluate {
//	artifacts.add( "jarz", tasks[ "remapJar" ] )
//}
