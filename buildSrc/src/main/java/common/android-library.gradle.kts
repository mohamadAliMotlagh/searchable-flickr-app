package common

import BuildAndroidConfig
import Versions
import kapt

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}
android {
    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION
    namespace = BuildAndroidConfig.APPLICATION_ID
    defaultConfig {
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
       // targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_OPTION_VERSION
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {
//    implementation(Dependencies.KOTLIN)
//    api(Dependencies.HILT)
}