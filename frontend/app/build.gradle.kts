import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    namespace = "com.example.vishingguard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vishingguard"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Gradle.properties 파일에 정의된 API 키 사용
        buildConfigField(
            "String", "Google_MAP_API_KEY", properties.getProperty("Google_MAP_API_KEY")
        )
        buildConfigField(
            "String", "clientId", properties.getProperty("clientId")
        )
        buildConfigField(
            "String", "clientSecret", properties.getProperty("clientSecret")
        )
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
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")


    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation ("androidx.fragment:fragment-ktx:1.5.3")
    implementation ("androidx.activity:activity-ktx:1.6.0")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")

    // Indicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    // Google Map
    implementation ("com.google.android.gms:play-services-location:17.0.0")
    implementation ("com.google.android.gms:play-services-maps:17.0.0")

    // OK
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
}