import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "groupId"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("uk.co.electronstudio.jaylib:jaylib:5.0.0-0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.0")
    implementation("com.github.weisj:darklaf-core:3.0.2")
    implementation("org.json:json:20231013")
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.0.0")
    implementation("org.openjfx:javafx-controls:17.0.6")
    implementation("com.moandjiezana.toml:toml4j:0.7.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.openjfx:javafx-fxml:17.0.6")
    implementation("org.yaml:snakeyaml:2.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}

application {
    mainClass.set("MainKt") // Укажите ваш главный класс здесь
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}