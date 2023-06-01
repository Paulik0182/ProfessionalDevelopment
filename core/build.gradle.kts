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

    // Scope
//    implementation (Koin.koin_scope)
//    implementation (Koin.koin_viewmodel)
//    implementation (Koin.koin_fragment)
//    implementation (Koin.koin_workmanager)
//    implementation (Koin.koin_compose)
//    implementation (Koin.koin_androidx_ext)
}