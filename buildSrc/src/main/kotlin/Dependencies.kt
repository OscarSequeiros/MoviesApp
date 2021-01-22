object AndroidSdk {
    const val min = 23
    const val compile = 30
    const val buildTools = "30.0.2"
}

object AndroidLibraries {
    object Versions {
        const val core = "1.3.1"
        const val appCompat = "1.2.0"
        const val material = "1.2.1"
        const val constraintLayout = "2.0.4"
    }

    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object KotlinLibraries {
    object Versions {
        const val kotlin = "1.4.21"
        const val jvmTarget = "1.8"
    }

    const val kotlinStdLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object TestLibraries {
    object Versions {
        const val jUnit = "4.13.1"
    }

    const val jUnit = "junit:junit:${Versions.jUnit}"
}

object TestAndroidLibraries {
    object Versions {
        const val androidJUnit = "1.1.2"
        const val androidEspresso = "3.3.0"
    }

    const val androidJUnit = "androidx.test.ext:junit:${Versions.androidJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidEspresso}"
}