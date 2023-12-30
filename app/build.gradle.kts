plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.applicationscalp_care"
    compileSdk = 34

    viewBinding {
        enable = true
    }

    defaultConfig {
        applicationId = "com.example.applicationscalp_care"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("com.android.volley:volley:1.2.1") // 네트워크 통신 라이브러리 추가
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.kakao.sdk:v2-user:2.12.0") // 카카오 로그인

    implementation (files("libs/libDaumMapAndroid.jar")) // Kakao maps sdk
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Glide 라이브러리 추가
    //:이미지를 효율적으로 빠르게 불러오는 라이브러리
    annotationProcessor("com.github.bumptech.glide:compiler:4.10.0")
    implementation("com.github.bumptech.glide:glide:4.14.2")

}
