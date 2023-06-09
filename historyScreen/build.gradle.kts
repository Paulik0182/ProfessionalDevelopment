plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

dependencies {

    // Modules
    implementation(project(Modules.model))
    implementation(project(Modules.repository))
    implementation(project(Modules.core))

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
}