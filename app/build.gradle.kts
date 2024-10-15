plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.saadapps.emicalculator.loan.finance.tool"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.saadapps.emicalculator.loan.finance.tool"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding=true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Material SearchBar
    implementation ("com.github.mancj:MaterialSearchBar:0.8.5")

    //lottie animation
    implementation ("com.airbnb.android:lottie:3.4.0")
    //Charts library
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //Indicators
    implementation(libs.circleindicator)
    //Sdp
    implementation(libs.sdp)
    //UpdateApp
    implementation (libs.app.update)
}