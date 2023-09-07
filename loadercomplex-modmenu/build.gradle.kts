import xyz.wagyourtail.unimined.api.unimined

repositories {
	maven( url = "https://maven.terraformersmc.com/releases" )
	maven( url = "https://maven.quiltmc.org/repository/release" )
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
	"modImplementation"( "com.terraformersmc:modmenu:3.1.0" ) {
		isTransitive = false
	}
}
