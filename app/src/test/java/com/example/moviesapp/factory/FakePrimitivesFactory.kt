package com.example.moviesapp.factory

import kotlin.random.Random

object FakePrimitivesFactory {

    fun generateRandomString(length: Int = 20): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun generateRandomDouble(max: Double = Double.MAX_VALUE): Double {
        return Random.nextDouble(0.0, max)
    }

    fun generateRandomLong(max: Long = Long.MAX_VALUE): Long {
        return Random.nextLong(0, max)
    }
}