@file:Suppress("UnstableApiUsage", "PropertyName")
val mappings: String by project
val minecraft_version: String by project
val modmenu_version: String by project
val loader_version: String by project

repositories {
	maven( url = "https://maven.quiltmc.org/repository/release" )
	maven( url = "https://maven.quiltmc.org/repository/snapshot" )
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft( "com.mojang:minecraft:$minecraft_version" )
	mappings( "org.quiltmc:quilt-mappings:$minecraft_version+build.$mappings:intermediary-v2" )
	modImplementation( "org.quiltmc:quilt-loader:$loader_version" )
	modImplementation( "com.terraformersmc:modmenu:$modmenu_version" ) {
		isTransitive = false
	}
}
