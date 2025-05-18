plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt") // Habilitar Kapt
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
}

ktlint {
    version.set("1.2.1") // usa la versión que prefieras
    android.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(false)
}

android {
    namespace = "com.rpla.marsrovernavigator"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rpla.marsrovernavigator"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    kapt {
        correctErrorTypes = true
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE.txt")
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
            excludes.add("META-INF/LICENSE-notice.md")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
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
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.constraintlayout.compose)
    implementation(libs.material.icons.core) // Conjunto básico de iconos
    implementation(libs.material.icons.extended) // Conjunto extendido de iconos
    implementation(libs.androidx.splashscreen)
    implementation(libs.coroutines.core) // Corrutinas estándar
    implementation(libs.coroutines.android) // Soporte para Android
    implementation(libs.hilt.android) // Core de Hilt
    implementation(libs.androidx.annotation) // Para soporte de anotaciones
    implementation(libs.androidx.ui.test.android) // Soporte KTX (incluye MasterKey)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.hamcrest)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk) // Para tests unitarios
    androidTestImplementation(libs.mockk.android) // Para tests instrumentados
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.compose.ui.tooling) // Herramientas de inspección en modo debug
    debugImplementation(libs.compose.ui.test.manifest) // Soporte para tests UI en modo debug
}
