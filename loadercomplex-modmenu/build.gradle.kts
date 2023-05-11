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
	maven( url = "https://maven.quiltmc.org/repository/release" )
	maven( url = "https://maven.quiltmc.org/repository/snapshot" )
}

unimined {
	useGlobalCache.set( false )
}
minecraft {
	quilt {

	}

	mcRemapper.tinyRemapperConf = { builder: net.fabricmc.tinyremapper.TinyRemapper.Builder ->
		// most mcp mappings (except older format) dont include field desc
		builder.ignoreFieldDesc(true)
		// this also fixes some issues with them, as it tells tiny remapper to try harder to resolve conflicts
		builder.ignoreConflicts(true)
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft( "net.minecraft:minecraft:$minecraft_version" )
	mappings( "org.quiltmc:quilt-mappings:$minecraft_version+build.$mappings:intermediary-v2" )
	"quilt"( "org.quiltmc:quilt-loader:$loader_version" )
//	modImplementation( "com.terraformersmc:modmenu:$modmenu_version" ) {
//		isTransitive = false
//	}
}
