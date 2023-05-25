plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

dependencies {

    // Models
//    implementation (project(Modules.app))
    implementation(project(Modules.model))
    implementation(project(Modules.repository))
    implementation(project(Modules.core))

    // Kotlin
    implementation(Kotlin.stdlib)
    implementation(Kotlin.coroutines_core)
    implementation(Kotlin.coroutines_android)
    // Android X
    implementation(AndroidX.core)
    // CardView
    implementation(CardView.appcompat)
    // Test
    testImplementation(TestImpl.junit_test)
    androidTestImplementation(TestImpl.ext_junit_test)
    androidTestImplementation(TestImpl.espresso_core_test)
//    implementation (fileTree (dir: "libs", include: ["*.jar"])) // todo

    // Material
    implementation(Material.material)
    implementation(Material.swi_pere_fresh_layout)

    //Room
    implementation(Room.room_runtime)
    kapt(Room.kapt_room_compiler)
    implementation(Room.room_ktx)
    // optional - RxJava2 support for Room
    implementation(Room.room_rxjava2)
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation(Room.room_guava)
    // optional - Test helpers
    implementation(Room.room_testing)

    //Koin
    //Koin core features
    implementation(Koin.koin_core)
    //Koin main features for Android (Scope,ViewModel ...)
    implementation(Koin.koin_android)
    //Koin Java Compatibility
    implementation(Koin.android_compat)
}