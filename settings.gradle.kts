@file:Suppress("UnstableApiUsage")
pluginManagement.repositories {
	mavenCentral()
	maven( url="https://maven.fabricmc.net" )
	maven( url="https://maven.neoforged.net/releases" )
	maven( url="https://maven.minecraftforge.net" )
	maven( url="https://maven.wagyourtail.xyz/releases" )
	maven( url="https://maven.wagyourtail.xyz/snapshots" )
	gradlePluginPortal {
		content { excludeGroup( "org.apache.logging.log4j" ) }
	}
}

include( "loadercomplex-api" )
include( "loadercomplex-common" )
include( "loadercomplex-test" )

//include( "loadercomplex-quilt" )
include( "loadercomplex-forge" )
include( "loadercomplex-fabric" )

rootProject.name = "loadercomplex"
