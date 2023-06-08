import org.gradle.api.JavaVersion

object Config {
    const val namespace_application_id = "com.paulik.professionaldevelopment"
    const val compile_sdk = 33
    const val min_sdk = 28
    const val target_sdk = 33
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_name = "1.0"
    val version_code = getVersionName(version_name)

    private fun getVersionName(version: String): Int {
        val parts = version.split(".")
        return parts[0].toInt() * 1_000 + parts[1].toInt() * 10
    }
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"
    //Features
    const val historyScreen = ":historyScreen"
}

object Versions {

    //Kotlin
    const val stdlib = "1.8.20"
    const val coroutinesCore = "1.6.4"
    const val coroutinesAndroid = "1.6.4"

    // Android X
    const val core = "1.10.1"
    const val constraintLayout = "2.1.4"
    // splash Screen
    const val splashScreen = "1.0.1"

    // cardView
    const val appcompat = "1.6.1"
    const val cardView = "1.0.0"

    // recyclerView
    const val recyclerView = "1.3.0"

    // viewModel
    const val fragmentKtx = "1.5.7"

    //LiveData
    const val liveData = "2.6.1"

    // Material
    const val material = "1.9.0"
    const val swiPereFreshLayout = "1.1.0"

    // RXJava
    const val rxAndroid = "2.1.0"
    const val rxJava = "2.2.9"

    // Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val retrofit2Rxjava2Adapter = "1.0.0"
    const val retrofit2KotlinCoroutinesAdapter = "0.9.2"

    // Okhttp
    const val okhttp = "4.9.3"
    const val okhttp3Logging = "4.9.2"

    //KOIN
    const val koin = "3.4.0"

    //Download images
    const val coil = "2.1.0"
    const val picasso = "2.71828"
    const val glide = "4.13.2"

    //Room
    const val room = "2.5.1"

    // Test
    const val junitTest = "4.13.2"
    const val extJunitTest = "1.1.5"
    const val espressoCoreTest = "3.5.1"

    //Google Play
    const val googlePlayCore = "1.10.3"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object AndroidX {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val splash_screen = "androidx.core:core-splashscreen:${Versions.splashScreen}"

}

object CardView {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val card_view = "androidx.cardview:cardview:${Versions.cardView}"
}

object RecyclerView {
    const val recycler_view = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
}

object ViewModel {
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
}

object LiveData {
    const val live_data = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"
}

object Material {
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swi_pere_fresh_layout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiPereFreshLayout}"
}

object RXJava {
    const val rx_android = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.rxAndroid}"
    const val rx_java = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.rxJava}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val retrofit2_rxjava2_adapter =
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:${Versions.retrofit2Rxjava2Adapter}"
    const val retrofit2_kotlin_coroutines_adapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${
            Versions.retrofit2KotlinCoroutinesAdapter
        }"
}

object Okhttp {
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttp3_logging =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Logging}"
}

object Koin {
    const val koin_core = "io.insert-koin:koin-core:${Versions.koin}"
    const val koin_android = "io.insert-koin:koin-android:${Versions.koin}"
    const val android_compat = "io.insert-koin:koin-android-compat:${Versions.koin}"
}

object DownloadImages {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val kapt_glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object Room {
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val kapt_room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val room_rxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
    const val room_guava = "androidx.room:room-guava:${Versions.room}"
    const val room_testing = "androidx.room:room-testing:${Versions.room}"
}

object GooglePlay {
    const val google_play_core = "com.google.android.play:core:${Versions.googlePlayCore}"
}

object TestImpl {
    const val junit_test = "junit:junit:${Versions.junitTest}"
    const val ext_junit_test = "androidx.test.ext:junit:${Versions.extJunitTest}"
    const val espresso_core_test =
        "androidx.test.espresso:espresso-core:${Versions.espressoCoreTest}"
}