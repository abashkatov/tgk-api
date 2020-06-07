import org.hibernate.orm.tooling.gradle.EnhanceExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ru.adv2ls"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val db_url: String by project
val db_username: String by project
val db_password: String by project
val jjwtVersion: String by project

plugins {
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.hibernate.orm") version "5.4.9.Final"
    id ("org.flywaydb.flyway") version "6.3.2"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
    kotlin("plugin.noarg") version "1.3.71"
    kotlin("plugin.jpa") version "1.3.71"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.postgresql:postgresql:42.2.0")
    implementation ("org.springframework.security:spring-security-test")
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

flyway {
    url = db_url
    user = db_username
    password = db_password
    baselineOnMigrate = true
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

noArg {
    annotation("com.my.Annotation")
}

hibernate {
    enhance (closureOf<EnhanceExtension>{
        enableLazyInitialization = true
        enableDirtyTracking = true
        enableAssociationManagement = true
    })
}
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.register<Jar>("uberJar") {
    enabled = true
    archiveClassifier.set("uber")
    manifest {
        attributes("Main-Class" to "ru.adv2ls.communication.CommunicationApplicationKt")
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
//    from({
//        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
//    })
//    from ({
//        configurations.compile. .collect { it.isDirectory() ? it : zipTree(it) }
//    })
}
