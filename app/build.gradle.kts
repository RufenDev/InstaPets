plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //Dagger hilt y KAPT
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.instapets"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.instapets"
        minSdk = 27
        targetSdk = 34
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

            buildConfigField("String", "CAT_API_URL", "\"https://api.thecatapi.com/v1/\"")
            buildConfigField("String", "DOG_API_URL", "\"https://api.thedogapi.com/v1/\"")
        }

        debug{
            buildConfigField("String", "CAT_API_URL", "\"https://api.thecatapi.com/v1/\"")
            buildConfigField("String", "DOG_API_URL", "\"https://api.thedogapi.com/v1/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Circle Indicator
    implementation("me.relex:circleindicator:2.1.6")

    //Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Preferences
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Retrofit2
    val retrofitVersion = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:hilt-compiler:2.48.1")

    // StateFlow (coroutines)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Navigation Component
    val navVersion = "2.7.5"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    // SpinKit
    implementation("com.github.ybq:Android-SpinKit:1.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}