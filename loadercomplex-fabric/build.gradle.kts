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

val versions = listOf(
//	( "1.12.2" to "395" ) to "",
	( "1.17.1" to "65" ) to ""
)

repositories {
	maven( url="https://maven.fabricmc.net" )
}

fun <T : Any> unaryOf( body: T.() -> Unit ) =
	KotlinClosure1<T, T>( { body.invoke( this ); this }, null, null )

val ag: MinecraftAmalgamation by project
val loaderVersion = libs.versions.fabric.loader.get()
for ( ( versions, intemerdiary ) in versions ) {
	val ( minecraftVersion, mapping ) = versions
	val tag = minecraftVersion.split(".")[1]
	sourceSets.create( "fabric$tag" ) {
		this.ext
	}

	val intermediate = ag.map {
		println( minecraftVersion )
		mappings( ag.intermediary( minecraftVersion ) )
		inputGlobal( ag.mojmerged( minecraftVersion ) )
	}

	val map = "net.fabricmc:yarn:$minecraftVersion+build.$mapping:v2"
	lateinit var mappedMc: Dependency
	lateinit var fapi: Dependency
	val floader = ag.fabricLoader( loaderVersion )
	ag.map {
		mappings(map, "intermediary", "named")
		mappedMc = inputGlobal(intermediate) as Dependency
		fapi = inputLocal(
			"net.fabricmc.fabric-api:fabric-api:${libs.versions}",
			unaryOf { exclude( module="fabric-loader") }
		) as Dependency
	}

	val excluded = configurations.create( "excluded" )
	configurations.implementation.get().extendsFrom( excluded )

	dependencies {
		excluded( mappedMc )
		implementation( fapi )
		// implementation( rei )
		excluded( ag.libraries(minecraftVersion) )
		excluded( floader )
		excluded( map )
	}

	tasks.create( "remapJar", RemapJar::class ) {
		group = "build"
		with( tasks.jar.get() )
		classpath.set( sourceSets.main.get().compileClasspath )
		useExperimentalMixinRemapper()
		mappings(map, "named", "intermediary")
	}

	tasks.create( "remapSourcesJar", RemapSourcesJar::class ) {
		group = "build"
		archiveClassifier.set( "sources" )
		from( sourceSets.main.get().allSource )
		classpath.set( sourceSets.main.get().compileClasspath )
		mappings(map, "named", "intermediary")
	}

	val runClient = tasks.create("runClient", JavaExec::class) {
		group = "Minecraft"
		description = "runs minecraft"
		classpath( sourceSets.main.get().runtimeClasspath )
		mainClass.set( "net.fabricmc.loader.launch.knot.KnotClient" )
		val natives = ag.natives( minecraftVersion )
		systemProperty( "fabric.development", true )
		systemProperty( "fabric.gameVersion", minecraftVersion )
		systemProperty( "java.library.globalCache", natives )
		systemProperty( "org.lwjgl.librarypath", natives )
		val assets = ag.assets( minecraftVersion )
		args("--assetIndex", assets.getAssetIndex(), "--assetsDir", assets.getAssetsDir())
		workingDir("$rootDir/run")
		dependsOn( tasks.classes )
	}

	ag.idea().java(runClient) {
		setJvmVersion("17")
		overrideClasspath( project, sourceSets.main.get() )
		excludeDependency( tasks.classes.get() )
	}
}
