plugins {
	`maven-publish`
}

version = "" // api_version

repositories {
	mavenLocal()
	mavenCentral()
	maven( url="https://repsy.io/mvn/enderzombi102/mc" )
}

dependencies {
	implementation( libs.slf4j )
	implementation( libs.guava )
	implementation( libs.jankson )
	compileOnly( libs.brigadier )
	compileOnly( libs.eventsystem )
	compileOnly( libs.enderlib )
}

tasks.withType<JavaCompile>() {
	sourceCompatibility = "8"
	options.encoding = "UTF-8"
	options.release.set( 8 )
}

artifacts {
	jarz( tasks.jar )
}

java {
	withJavadocJar()
	withSourcesJar()
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			group = "com.enderzombi102"
			artifactId = "loadercomplex-api"
			from( components["java"] )
		}
	}

	repositories {
		mavenLocal()
		maven {
			name = "Repsy"
			credentials(PasswordCredentials::class)
			url = uri("https://repsy.io/mvn/enderzombi102/mc")
		}
	}
}