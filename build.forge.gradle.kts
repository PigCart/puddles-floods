plugins {
    id("net.neoforged.moddev.legacyforge")
}

val minecraft = property("deps.minecraft") as String;
fun prop(name: String) = project.property(name) as String

tasks.named<ProcessResources>("processResources") {
    val props = HashMap<String, String>().apply {
        this["mod_id"] =        prop("mod.id")
        this["mod_name"] =      prop("mod.name")
        this["mod_version"] =   prop("mod.version")
        this["mod_description"]=prop("mod.description")
        this["mod_author"] =    prop("mod.author")
        this["mod_sources"] =   prop("mod.sources")
        this["mod_issues"] =    prop("mod.issues")
        this["mod_homepage"] =  prop("mod.homepage")
        this["mod_license"] =   prop("mod.license")
        this["mod_icon"] =      prop("mod.icon")
        this["version_range"] = prop("version_range")

        // insert version-specific mixins
        this["FluidRendererImplMixin" ] = "" // >1.20.1 only
        this["ChunkBuilderMeshingTaskMixin"] = "\"sodium.ChunkBuilderMeshingTaskMixin\","// <21.6
        this["DefaultFluidRendererMixin"   ] = "\"sodium.DefaultFluidRendererMixin\","// <21.6
        this["BreakingBlockEffectMixin"    ] = "\"client.BreakingBlockEffectMixin\"," // <1.21.9
    }

    filesMatching(listOf("META-INF/mods.toml", "${prop("mod.id")}.mixins.json")) {
        expand(props)
    }
}

version = "${prop("mod.version")}+${minecraft}-forge"
base.archivesName = prop("mod.id")

repositories {
    mavenLocal()
    maven("https://maven.neoforged.net/releases/")
    maven("https://maven.parchmentmc.org")
    maven("https://api.modrinth.com/maven")
    maven("https://maven.su5ed.dev/releases")
}

legacyForge {
    version = prop("deps.forge")

    val accessTransformer = rootProject.file("src/main/resources/META-INF/accesstransformer.cfg")
    if (accessTransformer.exists()) {
        accessTransformers.from(accessTransformer)
    }

    if (hasProperty("deps.parchment")) parchment {
        val (mc, ver) = (prop("deps.parchment")).split(':')
        mappingsVersion = ver
        minecraftVersion = mc
    }

    mods {
        register(prop("mod.id")) {
            sourceSet(sourceSets["main"])
        }
    }

    runs {
        register("client") {
            gameDirectory = file("run/")
            client()
        }
        register("server") {
            gameDirectory = file("run/")
            server()
        }
    }
}

mixin {
    add(sourceSets.main.get(), "${prop("mod.id")}.refmap.json")
    config("${prop("mod.id")}.mixins.json")
}

dependencies {
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")!!)
    implementation(jarJar("io.github.llamalad7:mixinextras-forge:0.5.0")) {}
    // ffapi doesnt actually function correctly on oldforge this is just here so it compiles
    compileOnly("dev.su5ed.sinytra.fabric-api:fabric-api:${property("deps.forgified_fabric_api")}")
    // sodium port
    compileOnly("maven.modrinth:embeddium:${property("deps.embeddium")}")
}

tasks {
    processResources {
        exclude("**/fabric.mod.json", "**/*.accesswidener", "**/neoforge.mods.toml")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${prop("mod.version")}"))
        dependsOn("build")
    }

    jar {
        manifest.attributes["MixinConfigs"] = "${prop("mod.id")}.mixins.json"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}