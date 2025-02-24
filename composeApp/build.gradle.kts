import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

kotlin {
    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.firebase.android.bom)

            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)
        }
        commonMain.dependencies {
            api(libs.gitlive.firebase.kotlin.crashlytics)
            implementation(projects.core.datastore)
            implementation(projects.core.navigation)
            implementation(projects.core.ui)
            implementation(projects.core.domain)
            implementation(projects.core.storage.data)
            implementation(projects.sharedResources)
            implementation(projects.feature.security.data)
            implementation(projects.feature.security.ui)
            implementation(projects.feature.security.domain)

            implementation(projects.feature.auth.ui)

            implementation(projects.feature.preferences.domain)
            implementation(projects.feature.preferences.data)
            implementation(projects.feature.preferences.ui)

            implementation(projects.feature.welcome.ui)
            implementation(projects.feature.freudScore.ui)
            implementation(projects.feature.onboarding.ui)

            implementation(projects.feature.home.ui)

            implementation(projects.feature.user.data)
            implementation(projects.feature.user.domain)
            implementation(projects.feature.user.ui)

            implementation(projects.feature.mood.ui)
            implementation(projects.feature.mood.domain)
            implementation(projects.feature.mood.data)

            implementation(projects.feature.selfJournal.ui)
            implementation(projects.feature.selfJournal.domain)
            implementation(projects.feature.selfJournal.data)

            implementation(projects.feature.sleepQuality.ui)
            implementation(projects.feature.sleepQuality.domain)
            implementation(projects.feature.sleepQuality.data)

            implementation(projects.feature.stressLevel.ui)
            implementation(projects.feature.stressLevel.domain)
            implementation(projects.feature.stressLevel.data)

            implementation(projects.feature.splash.ui)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlin.datetime)
            implementation(libs.navigation.compose)

            implementation(libs.bundles.koin)
        }
    }
}

android {
    namespace = libs.versions.android.packageName.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = libs.versions.android.packageName.get()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.android.versionCode.get().toInt()
        versionName = libs.versions.android.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk.debugSymbolLevel = "FULL"
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    base {
        archivesName = "Moodly-v${defaultConfig.versionCode}"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    ndkVersion = "27.0.12077973"
}
