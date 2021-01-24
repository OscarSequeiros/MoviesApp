package com.example.moviesapp.data

import com.example.moviesapp.data.local.MoviesLocalSource
import com.example.moviesapp.data.mapper.DataMovieMapper
import com.example.moviesapp.data.remote.MoviesRemoteSource
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MoviesDataRepository(
        private val remoteSource: MoviesRemoteSource,
        private val localSource: MoviesLocalSource,
        private val mapper: DataMovieMapper
) : MoviesRepository {

    override suspend fun getMovieById(id: Long): Movie {
        val localMovie = localSource.getMovieById(id)
        return mapper.toDomain(localMovie)
    }

    override fun getPopularMovies(): Flow<List<Movie>> {
        return flow {
            val remoteMovies = remoteSource.getPopularMovies()
            localSource.insertMovies(mapper.toLocal(remoteMovies))

            localSource.getAllMovies()
                .map { localMovies -> mapper.toDomain(localMovies) }
                .collect { emit(it) }
        }
    }
}