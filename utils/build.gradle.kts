plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
}

dependencies {
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
}