plugins {
    java
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.springdoc.openapi-gradle-plugin") version "1.6.0"
}

group = "ilyes.de"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_19

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.jetbrains:annotations:20.1.0")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.apache.logging.log4j:log4j-layout-template-json:2.19.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

extra["springCloudVersion"] = "2022.0.0"
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}


configurations.all {
        exclude("org.springframework.boot","spring-boot-starter-logging")
}

openApi {
    apiDocsUrl.set("http://localhost:8080/api-docs.yaml")
    outputDir.set(file("${project.projectDir}/src/main/resources/openapi"))
    outputFileName.set("open-api.yaml")
    waitTimeInSeconds.set(10)
}

afterEvaluate {
    tasks.register("deleteOpenApiFile") {
        delete("${project.projectDir}/src/main/resources/openapi/open-api.yaml")
    }
    tasks.getByName("generateOpenApiDocs").dependsOn(":deleteOpenApiFile")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
