plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ubayadev.eventubaya"
    compileSdk {
        version = release(37)
    }

    viewBinding {
        enable = true
    }

    defaultConfig {
        applicationId = "com.ubayadev.eventubaya"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.androidx.viewpager2)
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("com.squareup.picasso:picasso:2.71828")
}
