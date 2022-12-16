@file:Suppress("UnstableApiUsage")
plugins {
	`maven-publish`
}

version = project.ext["api"] as String

dependencies {
	implementation( libs.slf4j )
	implementation( libs.jankson )
	implementation( libs.brigadier )
	implementation( libs.eventsystem )
	compileOnly( libs.enderlib )
}

java.withJavadocJar()
java.withSourcesJar()
artifacts.jarz(tasks.jar)

tasks.withType<JavaCompile> {
	sourceCompatibility = "8"
	options.encoding = "UTF-8"
	options.release.set(8)
}

tasks.withType<Javadoc> {
	exclude( "com/enderzombi102/loadercomplex/impl/**" )
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			group = "com.enderzombi102"
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
