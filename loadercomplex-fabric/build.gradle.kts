val versions = listOf(
	Triple( "legacy", "1.12.2", "395" ),
	Triple( "modern", "1.17.1", "65" )
)

repositories {
	maven( url="https://maven.terraformersmc.com/releases/" )
}

sourceSets {
	for ( ( version, mapping ) in versions )
		create( version ) { }
}


dependencies {
//	minecraft( "com.mojang:minecraft:1.12.2" )
}
