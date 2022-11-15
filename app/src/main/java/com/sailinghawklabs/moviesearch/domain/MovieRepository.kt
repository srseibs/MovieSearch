package com.sailinghawklabs.moviesearch.domain

import com.sailinghawklabs.moviesearch.domain.model.Movie

interface MovieRepository {

    suspend fun searchMovies(query: String, page: Int = 1): Result<List<Movie>>
}