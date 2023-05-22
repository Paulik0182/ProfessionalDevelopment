plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

dependencies {

    implementation(project(Modules.model))

    // Kotlin
    implementation(Kotlin.stdlib)
    // Android X
    implementation(AndroidX.core)
    // CardView
    implementation(CardView.appcompat)
    // Test
    testImplementation(TestImpl.junit_test)
    androidTestImplementation(TestImpl.ext_junit_test)
    androidTestImplementation(TestImpl.espresso_core_test)
//    implementation (fileTree (dir: "libs", include: ["*.jar"])) // todo

    // Retrofit
    implementation(Retrofit.retrofit) // основная библиотека Retrofit
    // конвертер ответа сервера в объекты Gson
    implementation(Retrofit.converter_gson)
    implementation(Retrofit.retrofit2_rxjava2_adapter)
    implementation(Retrofit.retrofit2_kotlin_coroutines_adapter)

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

    // Okhttp
    implementation(Okhttp.okhttp)
    // адаптер RX Java 2 для Retrofit
    implementation(Okhttp.okhttp3_logging)
}