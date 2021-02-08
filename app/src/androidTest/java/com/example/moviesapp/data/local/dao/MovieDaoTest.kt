package com.example.moviesapp.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.moviesapp.data.local.database.MoviesAppDataBase
import com.example.moviesapp.factory.FakeMovieFactory.makeFakeLocalMovie
import com.example.moviesapp.factory.FakeMovieFactory.makeFakeLocalMovies
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: MoviesAppDataBase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.movieDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Ignore("Pending for checking")
    @Test
    fun given_localMovie_list_when_insertAll_then_update_database() = runBlocking {
        val moviesToSave = makeFakeLocalMovies(4)

        dao.insertAll(moviesToSave)

        dao.getAllPopulars().collect { savedMovies ->
            assert(savedMovies.contains(moviesToSave))
        }
    }

    @Test
    fun given_localMovie_when_get_by_id_then_get_it() = runBlocking {
        val movieToSave = makeFakeLocalMovie()
        dao.insertAll(listOf(movieToSave))

        val movie = dao.getById(movieToSave.id)

        assert(movie == movieToSave)
    }
}