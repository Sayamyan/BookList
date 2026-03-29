plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "1.9.22"
}

android {
    namespace = "com.coding.booklist"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.coding.booklist"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // UI
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    // RecyclerView
    implementation(libs.androidx.recyclerview)
    // Swipe Refresh
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    // Serialization (Content Negotiation) plugin
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)
    // Logging (optional, but highly recommended for debugging)
    implementation(libs.ktor.client.logging)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // Image loading
    implementation(libs.glide)
    ksp(libs.glide.compiler)
    implementation(libs.glide.transformations)
    // Logging
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.navigation.fragment)
    //Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}