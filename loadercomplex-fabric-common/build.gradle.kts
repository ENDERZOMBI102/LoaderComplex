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

artifacts {
    jarz( tasks.jar )
}
