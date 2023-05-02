@file:Suppress("UnstableApiUsage", "PropertyName")
import net.fabricmc.loom.configuration.ide.RunConfigSettings

val mappings: String by project
val minecraft_version: String by project
val modmenu_version: String by project
val loader_version: String by project
val qfapi_version: String by project

repositories {
	maven( url = "https://maven.quiltmc.org/repository/release" )
	maven( url = "https://maven.quiltmc.org/repository/snapshot" )
}

// little workaround until quolt stabilizes loader plugins :3
listOf( "client", "server" )
	.asSequence()
	.map( loom.runConfigs::named )
	.map( Provider<RunConfigSettings>::get )
	.forEach { it.property("loader.experimental.allow_loading_plugins", "true" ) }

dependencies {
	// To change the versions see the gradle.properties file
	minecraft( "com.mojang:minecraft:$minecraft_version" )
	mappings( "org.quiltmc:quilt-mappings:$minecraft_version+build.$mappings:intermediary-v2" )
	modImplementation( "org.quiltmc:quilt-loader:$loader_version" )
	modImplementation( "org.quiltmc.quilted-fabric-api:quilted-fabric-api:$qfapi_version-$minecraft_version-SNAPSHOT" )
	modImplementation( "com.terraformersmc:modmenu:$modmenu_version" )
}

tasks.withType<ProcessResources> {
	inputs.property( "version", rootProject.version )
	inputs.property( "description", rootProject.description )
	inputs.property( "repo_url", rootProject.ext["repo_url"] )
	inputs.property( "loader_version", loader_version )

	filesMatching("quilt.mod.json") {
		expand(
			"version" to rootProject.version,
			"description" to rootProject.description,
			"loader_version" to loader_version,
			"repo_url" to rootProject.ext["repo_url"]
		)
	}
}
