plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)// For type-safe navigation
}

// Declare ktlint configuration
configurations.create("ktlint")

dependencies {
    add("ktlint", libs.ktlint) // assuming you're using Version Catalog (libs.ktlint)
    testImplementation(kotlin("test"))
}

// ktlint task
tasks.register<JavaExec>("ktlint") {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = configurations["ktlint"]
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt")
    jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
}

// ktlint report task
tasks.register<JavaExec>("ktlintReport") {
    group = "verification"
    description = "Check Kotlin code style with HTML report."
    classpath = configurations["ktlint"]
    mainClass.set("com.pinterest.ktlint.Main")
    val reportFile = layout.buildDirectory.file("reports/ktlint/ktlint.html")
    args = listOf(
        "src/**/*.kt",
        "--reporter=html,output=${reportFile.get().asFile.absolutePath}"
    )
    jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
}

// ktlint format task
tasks.register<JavaExec>("ktlintFormat") {
    group = "formatting"
    description = "Fix Kotlin code style deviations."
    classpath = configurations["ktlint"]
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
    jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
}

// Add ktlint check to `check` lifecycle
tasks.named("check") {
    dependsOn("ktlint")
}

android {
    namespace = "com.compose.base"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.compose.base"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("debugKey") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyPassword = "android"
            keyAlias = "androiddebugkey"
        }
    }

    buildTypes {
        debug {
            signingConfig == signingConfigs.getByName("debugKey")
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11) // This sets the JVM target for Kotlin to 11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    flavorDimensions += "version"
    productFlavors {
        create("dev") {
            dimension = "version"
            applicationId = "com.compose.base.dev"
            resValue("string", "app_name", "App_DEV")
            buildConfigField ("String", "HOST_URL", "\"https://dev.api.yourdomain.com/\"")
        }
        create("staging") {
            dimension = "version"
            applicationId = "jcom.compose.base.stag"
            resValue("string", "app_name", "App_STAG")
            buildConfigField ("String", "HOST_URL", "\"https://dev.api.yourdomain.com/\"")
        }
        create("prod") {
            dimension = "version"
            applicationId = "com.compose.base"
            resValue("string", "app_name", "App_PROD")
            buildConfigField ("String", "HOST_URL", "\"https://dev.api.yourdomain.com/\"")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.constraintlayout.compose)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // Hilt for Compose ViewModels
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.navigation.compose)
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // For viewModelScope
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // For JSON parsing
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor) // For network logging
    // Coil (Image Loading) - often useful with Compose
    implementation(libs.coil.compose)
    // KotlinX Serialization runtime library (this is separate from the plugin)
    implementation(libs.kotlinx.serialization.json)
    // Local Data replace sharePreference
    implementation(libs.androidx.datastore.preferences)
    //Life cycle
    implementation(libs.androidx.lifecycle.process)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}