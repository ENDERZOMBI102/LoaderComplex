@file:Suppress("UnstableApiUsage", "PropertyName")
repositories {
	maven( url = "https://maven.terraformersmc.com/releases" )
	maven( url = "https://maven.quiltmc.org/repository/release" )
}

unimined.minecraft {
	// check these on https://lambdaurora.dev/tools/import_quilt.html
	mappings.quilt( 26 )

	quilt {
		loader( "0.19.2-beta.6" )
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	"modImplementation"( "com.terraformersmc:modmenu:3.1.0" ) {
		isTransitive = false
	}
}
