import io.github.astrarre.amalgamation.gradle.plugin.minecraft.MinecraftAmalgamation
import io.github.astrarre.amalgamation.gradle.tasks.remap.RemapJar
import io.github.astrarre.amalgamation.gradle.tasks.remap.RemapSourcesJar

buildscript {
	dependencies {
		classpath("amalgamation-minecraft:amalgamation-minecraft.gradle.plugin:1.0.1.0") {
			exclude(module = "trieharder")
		}
		classpath("io.github.coolmineman:trieharder:0.2.0")
	}
}

plugins { idea }
apply( plugin = "amalgamation-minecraft" )

// region UTILITIES
fun <T : Any> unaryOf( body: T.() -> Unit ) =
	KotlinClosure1<T, T>( { body.invoke( this ); this }, null, null )

val ag: MinecraftAmalgamation by project
val loaderVersion = libs.versions.fabric.loader.get()

data class Version(
	val minecraft: String,
	val mappings: String,
	val api: String,
	val java: String = "17",
	val namespace: String = "net.fabricmc",
	val intermediary: String = "$namespace:intermediary:$minecraft:v2"
)

// endregion UTILITIES

val sourceSetData = Version( "1.18.1", "17", "net.fabricmc.fabric-api:fabric-api:0.45.1+1.18" )

val versions = listOf( sourceSetData )

repositories {
	maven( url="https://maven.fabricmc.net" )
}

val tag = sourceSetData.minecraft.split(".")[1]
logger.lifecycle( "Setting up multiversion fabric project..." )
logger.info( " - Setting up sourceSet for minecraft ${sourceSetData.minecraft} with mappings ${sourceSetData.mappings} and intermediary ${sourceSetData.intermediary}..." )

sourceSets.main.get().also { sourceSet ->
	val intermediate: Set<*> = ag.map {
		mappings( sourceSetData.intermediary, "official", "intermediary" )
		inputGlobal( ag.mojmerged( sourceSetData.minecraft ) )
	}

	val yarnMappingNotation = "${sourceSetData.namespace}:yarn:${sourceSetData.minecraft}+build.${sourceSetData.mappings}:v2"
	lateinit var mappedMinecraft: Any
	lateinit var fapi: Any
	val floader: List<Dependency> = ag.fabricLoader( loaderVersion )
	ag.map {
		mappings(yarnMappingNotation, "intermediary", "named")
		mappedMinecraft = inputGlobal( intermediate )
		fapi = inputLocal( sourceSetData.api, unaryOf { exclude( module="fabric-loader") } )
	}

	val excluded = configurations.create( "excluded" )
	configurations.implementation.get().extendsFrom( excluded )

	dependencies {
		excluded( mappedMinecraft )
		implementation( fapi )
		// implementation( rei )
		excluded( ag.libraries(sourceSetData.minecraft) )
		excluded( floader )
		excluded( yarnMappingNotation )
	}

	tasks.create( "remapJar$tag", RemapJar::class ) {
		group = "build"
		with( tasks.jar.get() )
		classpath.set( sourceSet.compileClasspath )
		useExperimentalMixinRemapper()
		mappings(yarnMappingNotation, "named", "intermediary")
	}

	tasks.create( "remapSourcesJar$tag", RemapSourcesJar::class ) {
		group = "build"
		archiveClassifier.set( "sources" )
		from( sourceSet.allSource )
		classpath.set( sourceSet.compileClasspath )
		mappings(yarnMappingNotation, "named", "intermediary")
	}

	val runClient = tasks.create( "runClient$tag", JavaExec::class ) {
		group = "LoaderComplex"
		description = "runs LoaderComplex with fabric on minecraft ${sourceSetData.minecraft}"
		classpath( sourceSet.runtimeClasspath )
		mainClass.set( "net.fabricmc.loader.launch.knot.KnotClient" )
		val natives = ag.natives( sourceSetData.minecraft )
		systemProperty( "fabric.development", true )
		systemProperty( "fabric.gameVersion", sourceSetData.minecraft )
		systemProperty( "java.library.globalCache", natives )
		systemProperty( "org.lwjgl.librarypath", natives )
		val assets = ag.assets( sourceSetData.minecraft )
		args("--assetIndex", assets.assetIndex, "--assetsDir", assets.assetsDir)
		workingDir("$rootDir/run")
		dependsOn( tasks.classes )
	}

	ag.idea().java(runClient) {
		setJvmVersion( sourceSetData.java )
		overrideClasspath( project, sourceSet )
		excludeDependency( tasks.classes.get() )
	}
}