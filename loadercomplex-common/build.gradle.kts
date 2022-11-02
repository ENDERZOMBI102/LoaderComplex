@file:Suppress("UnstableApiUsage")
plugins {
	`maven-publish`
}

version = "0.2.0"

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

artifacts {
	jarz( tasks.jar )
}

java {
	withJavadocJar()
	withSourcesJar()
}

tasks.withType<Javadoc> {
	exclude( "com/enderzombi102/loadercomplex/impl/**" )
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