plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
}

dependencies {

    // Modules
    implementation(project(Modules.model))
    implementation(project(Modules.utils))
    implementation(project(Modules.repository))
}