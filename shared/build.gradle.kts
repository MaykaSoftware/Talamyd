plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.9.0"
    id("com.squareup.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true

            export(libs.decompose)
            export(libs.essenty.lifecycle)
            export(libs.moko.resources.compose.ios)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.logging)
                implementation(libs.sqldelight.runtime)
                implementation(libs.kotlin.date.time)

                implementation(libs.decompose)
                implementation(libs.essenty.lifecycle)
                implementation(libs.essenty.stateKeeper)
                implementation(libs.essenty.instanceKeeper)
                implementation(libs.reaktive)
                implementation(libs.multi.settings)
                implementation(libs.multi.settings.coroutines)
                implementation(libs.security.crypto)

                implementation(libs.decompose.extensions.compose.jetpack)
                implementation(libs.decompose.extensions.compose.jetbrains)

                implementation(libs.ktor.serialization.json)
                implementation(libs.koin.core)

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                api(libs.androidx.appcompat)
                api(libs.activity.compose)
                api(libs.androidx.core)

                api(libs.ktor.client.android)
                api(libs.ktor.client.okhttp)
                api(libs.sqldelight.android.driver)

                api(libs.decompose)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                api(libs.ktor.client.darwin)
                api(libs.sqldelight.ios.native.driver)
                api(libs.decompose)
                implementation(libs.essenty.lifecycle)
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.mayka.talamyd.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.material3.core)
    implementation(libs.androidx.ui)
    commonMainApi(libs.moko.resources.compose.ios)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.mayka.talamyd"
    multiplatformResourcesClassName = "SharedRes"
    disableStaticFrameworkWarning = true
}
