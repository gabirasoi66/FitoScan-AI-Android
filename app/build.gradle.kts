
plugins {
    id("com.android.application")
}
android {
    namespace = "com.fitoscan.ai"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fitoscan.ai"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
implementation("com.squareup.okhttp3:okhttp:4.12.0")
}
