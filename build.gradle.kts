// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  id("com.google.gms.google-services") version "4.4.1" apply false
  id("com.google.dagger.hilt.android") version "2.48.1" apply false
  id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
  id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
  id("androidx.navigation.safeargs") version "2.7.7" apply false
}