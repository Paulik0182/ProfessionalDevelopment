plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
}

dependencies {

    implementation(project(Modules.model))
    implementation(project(Modules.utils))
    implementation(project(Modules.repository))

    // Android
    implementation(AndroidX.core)
    // Kotlin
    implementation(Kotlin.stdlib)
    implementation(Kotlin.coroutines_core)
    implementation(Kotlin.coroutines_android)
    // cardView
    implementation(CardView.appcompat)
    // Test
    testImplementation(TestImpl.junit_test)
    androidTestImplementation(TestImpl.ext_junit_test)
    androidTestImplementation(TestImpl.espresso_core_test)
//    implementation fileTree(dir: 'libs', include: ['*.jar'])

}