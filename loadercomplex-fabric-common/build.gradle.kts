@file:Suppress("UnstableApiUsage")

repositories {
    maven( url="https://maven.fabricmc.net" )
}

dependencies {
	compileOnly( libs.mixins )
    compileOnly( libs.fabric.loader )
}

tasks.withType<ProcessResources>() {
    inputs.property( "version", rootProject.version )
    inputs.property( "description", rootProject.description )
    inputs.property( "repo_url", rootProject.ext["repo_url"] )

    filesMatching("fabric.mod.json") {
        expand(
            Pair( "version", rootProject.version ),
            Pair( "description", rootProject.description ),
            Pair( "repo_url", rootProject.ext["repo_url"] )
        )
    }
}

tasks.withType<JavaCompile>() {
    sourceCompatibility = "8" // for the IDE support
    options.encoding = "UTF-8"
    options.release.set(8)
}

artifacts {
    jarz( tasks.jar )
}
