import xyz.wagyourtail.unimined.api.unimined

repositories {
	maven( url = "https://maven.terraformersmc.com/releases" )
	unimined.quiltMaven()
}

unimined.minecraft( sourceSets["main"] ) {
	version( "1.19.2" )

	// check these on https://lambdaurora.dev/tools/import_quilt.html
	mappings.quilt( "22" )
	fabric {
		loader( "0.16.9" )
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	"modImplementation"( "com.terraformersmc:modmenu:4.2.0-beta.2" ) {
		isTransitive = false
	}
	"modImplementation"( "net.fabricmc:fabric-loader:0.16.9" )
	"modImplementation"( "net.fabricmc.fabric-api:fabric-api:0.77.0+1.19.2" )
}
