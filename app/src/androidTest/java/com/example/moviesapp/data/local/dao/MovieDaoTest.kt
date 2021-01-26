package com.example.moviesapp.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.moviesapp.data.local.database.MoviesAppDataBase
import com.example.moviesapp.data.local.model.LocalMovie
import com.example.moviesapp.factory.FakeMovieFactory.makeFakeLocalMovie
import com.example.moviesapp.factory.FakeMovieFactory.makeFakeLocalMovies
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MoviesAppDataBase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MoviesAppDataBase::class.java
        ).allowMainThreadQueries().build()

        dao = database.movieDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Ignore("To check what is the problem")
    @Test
    fun given_localMovie_list_when_insertAll_then_update_database() = runBlocking {
        val moviesToSave = makeFakeLocalMovies(4)

        dao.insertAll(moviesToSave)

        dao.getAllPopulars().collect { savedMovies: List<LocalMovie> ->
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