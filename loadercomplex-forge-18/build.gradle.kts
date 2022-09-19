@file:Suppress("UnstableApiUsage", "PropertyName")

val mappings: String by project
val minecraft_version: String by project
val forge_version: String by project

plugins {
	id("dev.architectury.loom") version "0.12.+"
}

loom {
	forge {
		mixinConfig( "loadercomplex-forge18.mixins.json" )
	}
}

dependencies {
	minecraft( "com.mojang:minecraft:$minecraft_version" )
	mappings( "net.fabricmc:yarn:$minecraft_version+$mappings:v2" )
//	forge( "net.minecraftforge:forge:$minecraft_version-$forge_version" )
}

tasks.withType<ProcessResources>() {
	inputs.property( "version", rootProject.version )
	inputs.property( "description", rootProject.description )
	inputs.property( "repo_url", rootProject.ext["repo_url"] )
	inputs.property( "forge_version", forge_version )

	filesMatching("META-INF/mods.toml") {
		expand(
			Pair( "version", rootProject.version ),
			Pair( "description", rootProject.description ),
			Pair( "forge_version", forge_version ),
			Pair( "repo_url", rootProject.ext["repo_url"] )
		)
	}
}
