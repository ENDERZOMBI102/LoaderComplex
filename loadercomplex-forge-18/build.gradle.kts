@file:Suppress("UnstableApiUsage", "PropertyName")
val mappings: String by project
val minecraft_version: String by project
val forge_version: String by project

loom.forge.mixinConfig( "loadercomplex-forge18.mixins.json" )

dependencies {
	minecraft( "com.mojang:minecraft:$minecraft_version" )
	mappings( "net.fabricmc:yarn:$minecraft_version+build.$mappings:v2" )
	forge( "net.minecraftforge:forge:$minecraft_version-$forge_version" )
}

tasks.withType<ProcessResources> {
	inputs.property( "version", rootProject.version )
	inputs.property( "description", rootProject.description )
	inputs.property( "repo_url", rootProject.ext["repo_url"] )
	inputs.property( "forge_version", forge_version )

	filesMatching("META-INF/mods.toml") {
		expand(
			"version" to rootProject.version,
			"description" to rootProject.description,
			"forge_version" to forge_version,
			"repo_url" to rootProject.ext["repo_url"]
		)
	}
}
