dependencies {
	compileOnly( project( ":loadercomplex-api" ) )
}

tasks.jar {
	manifest.attributes(
		"LoaderComplex-Id" to "testaddon",
		"LoaderComplex-Main" to "lctest.TestAddon",
		"LoaderComplex-Version" to archiveVersion,
		"LoaderComplex-BuildDate" to "ff"
	)
}
