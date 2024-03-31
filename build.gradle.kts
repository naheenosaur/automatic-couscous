plugins {
    id("org.springframework.boot") version ("3.0.2")
    id("io.spring.dependency-management") version ("1.0.11.RELEASE")
    id("io.freefair.lombok") version "8.4"
    id("jacoco")
    java
}

version = "0.0.0"
group = "com.naheenosaur"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

springBoot {
    buildInfo()
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor.get())
    }
}

sourceSets {
    main {
        java {
            srcDirs(arrayOf("${projectDir}/src/main/java", "${projectDir}/build/generated"))
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    runtimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.76.Final:osx-aarch_64")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
