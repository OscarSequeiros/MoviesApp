package com.example.moviesapp.data.remote

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.remote.model.RemoteMovie
import com.example.moviesapp.data.remote.model.RemoteMoviesResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class MoviesRemoteSource @Inject constructor() {

    private val client = HttpClient(CIO) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = HOST
                encodedPath = VERSION + url.encodedPath
            }
        }
        engine {
            endpoint {
                connectTimeout = TIMEOUT_MILLIS
            }
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    suspend fun getPopularMovies(): List<RemoteMovie> {
        val response: RemoteMoviesResponse = client.get(MOVIE_POPULAR_URL) {
            parameter(API_KEY_PARAM, BuildConfig.APP_KEY)
        }

        return response
            .results
            .map { movie -> movie.copy(poster_path = BASE_IMAGE_URL + movie.poster_path) }
    }

    companion object {
        const val HOST = "api.themoviedb.org"
        const val VERSION = "/3"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w400"
        const val API_KEY_PARAM = "api_key"
        const val MOVIE_POPULAR_URL = "movie/popular"
        const val TIMEOUT_MILLIS = 20_000L
    }
}