import xyz.wagyourtail.unimined.api.unimined

repositories {
	maven( url = "https://maven.terraformersmc.com/releases" )
	unimined.quiltMaven()
}

unimined.minecraft( sourceSets["main"] ) {
	version( "1.19.2" )

	// check these on https://lambdaurora.dev/tools/import_quilt.html
	mappings.quilt( "22" )
	quilt {
		loader( "0.20.2" )
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	"modImplementation"( "com.terraformersmc:modmenu:4.2.0-beta.2" ) {
		isTransitive = false
	}
	"modImplementation"( "org.quiltmc:quilt-loader:0.22.0-beta.1" )
	"modImplementation"( "org.quiltmc.quilted-fabric-api:quilted-fabric-api:4.0.0-beta.30+0.76.0-1.19.2" )
}
