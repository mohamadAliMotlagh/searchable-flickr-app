plugins {
    `kotlin-dsl`
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

repositories {
    google()
    mavenCentral()
}

object PluginsVersions {
    const val GRADLE_ANDROID = "7.4.0"
    const val KOTLIN = "1.7.20"
    const val NAVIGATION = "2.3.5"
    const val DAGGER = "2.40"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_ANDROID}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("com.squareup:javapoet:1.13.0")
}