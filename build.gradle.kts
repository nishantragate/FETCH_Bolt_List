// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://sdk-for-android.amazonwebservices.com/latest/aws-android-sdk.zip")
        }
        // Add other repositories if needed
    }
}

plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}