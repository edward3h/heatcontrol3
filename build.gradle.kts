plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.4"
    id("gg.jte.gradle") version "3.1.16"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("io.micronaut.test-resources") version "4.4.4"
    id("io.micronaut.aot") version "4.4.4"
}

version = "0.1"
group = "org.ethelred.heatcontrol3"

apply(from="gradle/asciidoc.gradle")
repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.soabase.record-builder:record-builder-processor:44")
    implementation("io.soabase.record-builder:record-builder-core:44")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.cache:micronaut-cache-caffeine")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.liquibase:micronaut-liquibase")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.toml:micronaut-toml")
    implementation("io.micronaut:micronaut-retry")
    implementation("gg.jte:jte:3.1.15")
    implementation("gg.jte:jte-models:3.1.15")
    runtimeOnly("com.mattbertolini:liquibase-slf4j:5.1.0")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.logevents:logevents:0.5.6")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
    jteGenerate("gg.jte:jte-models:3.1.15")
}


application {
    mainClass = "org.ethelred.heatcontrol3.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("23")
    targetCompatibility = JavaVersion.toVersion("23")
}


tasks {
    dockerBuild {
        images = listOf("${System.getenv("DOCKER_IMAGE") ?: project.name}:${project.version}")
    }

    dockerBuildNative {
        images = listOf("${System.getenv("DOCKER_IMAGE") ?: project.name}:${project.version}")
    }
}
graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("org.ethelred.heatcontrol3.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = false
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}

jte {
    sourceDirectory = file("src/main/jte").toPath()
    generate()
    packageName = "org.ethelred.heatcontrol3.template"
    jteExtension("gg.jte.models.generator.ModelExtension")
}

// Gradle requires that generateJte is run before some tasks
tasks.configureEach {
    if (name == "inspectRuntimeClasspath") {
        mustRunAfter("generateJte")
    }
}


