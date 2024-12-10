plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.yuktanam"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.yuktanam"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.firebase.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.material3.android)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.tflite.gpu)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.ui.test.junit4.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.google.material.v190)
    implementation(libs.glide)
    implementation(libs.circleimageview)
    implementation(libs.androidx.core.splashscreen)

    // RecyclerView
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)

    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)

    implementation(libs.androidx.exifinterface)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.room.runtime)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.android)
    testImplementation(libs.kotlinx.coroutines.test)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.play.services.auth.api.phone)

    // Compose
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)
    implementation(libs.androidx.activity.compose)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.ui.v150)
    implementation(libs.androidx.compose.foundation.foundation2)
    implementation(libs.androidx.material)

    // API KEY GEMINI
    implementation(libs.generativeai)

    // Lottie Animastion
    implementation(libs.lottie.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.ml.modeldownloader)

}