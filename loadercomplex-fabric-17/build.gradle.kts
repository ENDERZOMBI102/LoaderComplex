@file:Suppress("UnstableApiUsage", "PropertyName")

val mappings: String by project
val minecraft_version: String by project
val modmenu_version: String by project
val fabric_version: String by project

dependencies {
	minecraft( "com.mojang:minecraft:$minecraft_version" )
	mappings( "net.fabricmc:yarn:$minecraft_version+build.$mappings:v2" )
	modImplementation( libs.fabric.loader )

	modImplementation( "net.fabricmc.fabric-api:fabric-api:$fabric_version" )
	modImplementation( "com.terraformersmc:modmenu:$modmenu_version" )
	implementation( project(":loadercomplex-fabric-common") )
}
