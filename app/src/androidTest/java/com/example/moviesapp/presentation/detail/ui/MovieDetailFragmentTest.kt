package com.example.moviesapp.presentation.detail.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.pressBack
import androidx.test.filters.MediumTest
import com.example.moviesapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MovieDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun on_back_pressed() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MovieDetailFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()
    }
}