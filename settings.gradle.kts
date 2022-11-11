@file:Suppress("UnstableApiUsage")

enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven( url="https://jitpack.io" )
		maven( url="https://maven.fabricmc.net" )
		maven( url="https://maven.architectury.dev" )
		maven( url="https://maven.minecraftforge.net" )
		maven( url="https://repo.spongepowered.org/maven" )
		maven( url="https://files.minecraftforge.net/maven" )
		maven( url="https://storage.googleapis.com/devan-maven" )
		maven( url="https://maven.quiltmc.org/repository/release" )
		maven( url="https://maven.quiltmc.org/repository/snapshot" )
		maven( url="https://server.bbkr.space/artifactory/libs-release" )
		ivy {
			url = uri("https://github.com/CoolCrabs/brachyura/releases/download/v_962e7d8383220fdeff168b2ac521a6edda840c91")
			patternLayout {
				artifact("[artifact]-[revision](-[classifier])(.[ext])")
			}
			metadataSources.artifact()
		}
	}
	resolutionStrategy {
		eachPlugin {
			if ( requested.id.id == "net.minecraftforge.gradle" )
				useModule( "net.minecraftforge.gradle:ForgeGradle:${requested.version}" )
		}
	}
}

include("loadercomplex-api")
include("loadercomplex-forge-12")
include("loadercomplex-forge-18")
//include("loadercomplex-fabric-common")
//include("loadercomplex-fabric-12")
//include("loadercomplex-fabric-17")
include("loadercomplex-fabric")
include("loadercomplex-quilt")

rootProject.name = "loadercomplex"
