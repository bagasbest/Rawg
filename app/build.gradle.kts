plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.google.service)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.project.rawg"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.rawg"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        viewBinding = true
    }
    dynamicFeatures += setOf(":favorite")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.glide)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    ksp(libs.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.room.paging)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.library)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    //special instrumentation testing
    androidTestImplementation(libs.androidx.core.testing) // InstantTaskExecutorRule
    androidTestImplementation(libs.kotlinx.coroutines.test) //TestDispatcher
    //TestCoroutineDispatcher
    debugImplementation(libs.androidx.fragment.testing) //launchFragmentInContainer
    //mock web server
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.okhttp3.okhttp.tls)
    androidTestImplementation(libs.espresso.contrib) //RecyclerViewActions
    implementation(libs.androidx.espresso.idling.resource)
    implementation (libs.core.ktx)
    androidTestImplementation(libs.espresso.intents) //IntentsTestRule
    implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.lifecycle.reactivestreams.ktx)
    implementation(libs.android.sdk)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    androidTestImplementation(libs.koin.test.junit4)
    implementation(project(":core"))
    implementation(libs.feature.delivery.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.auth)
    implementation(libs.roundedimageview)
    debugImplementation(libs.library)
    debugImplementation(libs.leakcanary.android)
    implementation(libs.shimmer)
}