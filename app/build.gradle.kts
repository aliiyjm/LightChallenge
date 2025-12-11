import org.gradle.kotlin.dsl.implementation

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt") //necesario para Room
}

android {
    namespace = "com.example.lightchallenge"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lightchallenge"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // ✅ Jetpack Compose
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.5")
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.5")

    // ✅ Lifecycle + ViewModel (MVVM)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4") // Agregado para viewModel()
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

    // ✅ Room (CRUD local)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // ✅ Retrofit (API remota)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // ✅ Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // ✅ Material Icons (opcional)
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // ✅ Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.5")
}
