plugins {
    java
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "8.2.1"
}

group = "net.laaber"
version = "0.0.1-SNAPSHOT"
description = "Mars Weather API"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

spotless {
    java {
        palantirJavaFormat("2.87.0")

        // Optional formatting steps
        formatAnnotations()
        removeUnusedImports()
        expandWildcardImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-restclient")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")

    // RestAssured for testing
    testImplementation("io.rest-assured:rest-assured:6.0.0")
    testImplementation("io.rest-assured:json-path:6.0.0")
    testImplementation("io.rest-assured:xml-path:6.0.0")

    // Spring Boot Test
    testImplementation("org.springframework.boot:spring-boot-starter-restclient-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Optional: for JSON assertions
    testImplementation("io.rest-assured:json-schema-validator:6.0.0")

    testImplementation("org.wiremock:wiremock-standalone:3.13.2")

//    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
//    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
