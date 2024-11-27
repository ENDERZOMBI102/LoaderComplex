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

include("loadercomplex-api")
include("loadercomplex-common")
include("loadercomplex-modmenu")

//include("loadercomplex-quilt")

//include("loadercomplex-forge")
//include("loadercomplex-forge:ver12")
//include("loadercomplex-forge:ver18")

//include("loadercomplex-fabric")
//include("loadercomplex-fabric:ver12")
//include("loadercomplex-fabric:ver17")
//include("loadercomplex-fabric:common")

rootProject.name = "loadercomplex"
