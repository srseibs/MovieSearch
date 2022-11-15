package com.sailinghawklabs.moviesearch.data.remote.repository

import com.sailinghawklabs.moviesearch.data.remote.MovieApi
import com.sailinghawklabs.moviesearch.data.remote.response.toMovie
import com.sailinghawklabs.moviesearch.domain.MovieRepository
import com.sailinghawklabs.moviesearch.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val api: MovieApi,
) : MovieRepository {

    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                delay(2000)

                val result = api.searchMovies(queryString = query, page = page)
                Result.success(result.Search.map { it.toMovie() })

            } catch (e: Throwable) {
                Result.failure(e)
            }
        }

}