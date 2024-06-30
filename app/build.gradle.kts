plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  id("com.google.gms.google-services")
  id("com.google.dagger.hilt.android")
  id("com.google.devtools.ksp")
  id("kotlin-parcelize")
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
  id("androidx.navigation.safeargs.kotlin")
}

android {
  namespace = "com.rpsouza.movieapp"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.rpsouza.movieapp"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }

  viewBinding {
    enable = true
  }

  buildFeatures {
    buildConfig = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)

  // Splash API
  implementation(libs.androidx.core.splashscreen)

  // Firebase
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.database)
  implementation(libs.firebase.storage)
  implementation(libs.firebase.auth)

  // dagger hilt
  implementation(libs.hilt.android)
  ksp(libs.hilt.android.compiler)

  // Lifecycle
  implementation(libs.androidx.lifecycle.viewmodel.ktx)
  implementation(libs.androidx.lifecycle.livedata.ktx)

  // Navigation
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.navigation.ui.ktx)

  // https://github.com/bumptech/glide
  implementation(libs.glide)

  // Retrofit
  implementation(libs.retrofit)
  implementation(libs.converter.gson)

  // OkHttp
  implementation(platform(libs.okhttp.bom))
  implementation(libs.okhttp)
  implementation(libs.logging.interceptor)

  // https://github.com/Ferfalk/SimpleSearchView
  implementation(libs.simplesearchview)

  // Room
  implementation(libs.androidx.room.runtime)
  ksp(libs.androidx.room.compiler)
  implementation(libs.androidx.room.ktx)

  // Paging 3
  implementation(libs.androidx.paging.runtime)

  // https://github.com/facebookarchive/shimmer-android
  implementation(libs.shimmer)

  // Swipe Refresh Layout
  implementation(libs.androidx.swiperefreshlayout)

  // https://github.com/airbnb/lottie-android
  implementation(libs.lottie)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}