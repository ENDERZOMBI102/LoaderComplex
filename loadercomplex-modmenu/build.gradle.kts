@file:Suppress("UnstableApiUsage", "PropertyName")
plugins {
	java
	id( "xyz.wagyourtail.unimined" ) version "0.4.9"
}

val mappings: String by project
val minecraft_version: String by project
val modmenu_version: String by project
val loader_version: String by project

repositories {
	maven( url = "https://maven.terraformersmc.com/releases" )
	maven( url = "https://maven.quiltmc.org/repository/release" )
}

unimined.useGlobalCache.set( false )
minecraft {
	quilt()
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft( "net.minecraft:minecraft:$minecraft_version" )
	mappings( "net.fabricmc:intermediary:$minecraft_version:v2" )
	mappings( "org.quiltmc:quilt-mappings:$minecraft_version+build.$mappings:intermediary-v2" )
	"quilt"( "org.quiltmc:quilt-loader:$loader_version" )
	modImplementation( "com.terraformersmc:modmenu:$modmenu_version" ) {
		isTransitive = false
	}
}
