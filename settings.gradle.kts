@file:Suppress("UnstableApiUsage")
enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
		maven( url="https://maven.fabricmc.net" )
		maven( url="https://maven.wagyourtail.xyz/releases" ) //
		maven( url="https://maven.architectury.dev" )
		maven( url="https://maven.minecraftforge.net" )
		maven( url="https://files.minecraftforge.net/maven" )
		maven( url="https://maven.quiltmc.org/repository/release" )
	}
	resolutionStrategy.eachPlugin {
		if ( requested.id.id == "net.minecraftforge.gradle" )
			useModule( "net.minecraftforge.gradle:ForgeGradle:${requested.version}" )
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
