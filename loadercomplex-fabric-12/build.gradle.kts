@file:Suppress("UnstableApiUsage", "PropertyName")

val yarn_mappings: String by project
val minecraft_version: String by project
val modmenu_version: String by project

repositories {
	maven( url="https://jitpack.io" )
	maven( url="https://maven.legacyfabric.net" )
}

loom.intermediaryUrl.set( "https://maven.legacyfabric.net/net/fabricmc/intermediary/%1\$s/intermediary-%1\$s-v2.jar" )

dependencies {
	minecraft( "com.mojang:minecraft:$minecraft_version" )
	mappings( "net.legacyfabric:yarn:$minecraft_version+build.$yarn_mappings:v2" )
	modImplementation( libs.fabric.loader )

	modImplementation( "com.enderzombi102:modmenu-legacy:$modmenu_version+$minecraft_version" )
	implementation( project(":loadercomplex-fabric-common") )
}

tasks.withType<JavaCompile>() {
	options.encoding = "UTF-8"
	sourceCompatibility = "8"
	options.release.set(8)
}

artifacts {
	jarz( tasks["remapJar"] )
}
