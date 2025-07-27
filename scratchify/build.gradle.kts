import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.publish)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

mavenPublishing {
    coordinates("io.github.gsrathoreniks", "scratchify", "1.0.0-alpha2")

    pom {
        name.set("Scratchify")
        description.set("Scratchify is a lightweight and customizable scratch card SDK built using Jetpack Compose Multiplatform. It enables you to create interactive scratch surfaces where users can scratch off an overlay to reveal hidden content underneath. Ideal for rewards, discounts, surprise reveals, and gamification elements in your app!")
        inceptionYear.set("2025")
        url.set("https://github.com/gsrathoreniks/scratchify/")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("gsrathoreniks")
                name.set("Gajendra Singh Rathore")
                email.set("niks.gajsa@gmail.com")
                url.set("https://github.com/gsrathoreniks/")
            }
        }
        scm {
            url.set("https://github.com/gsrathoreniks/scratchify/")
            connection.set("scm:git:git://github.com/gsrathoreniks/scratchify.git")
            developerConnection.set("scm:git:ssh://git@github.com/gsrathoreniks/scratchify.git")
        }
    }
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "scratchify"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.gsrathoreniks.scratchify"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(libs.androidx.runtime.android)
    debugImplementation(compose.uiTooling)
}
