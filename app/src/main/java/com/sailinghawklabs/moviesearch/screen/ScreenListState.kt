package com.sailinghawklabs.moviesearch.screen

import com.sailinghawklabs.moviesearch.domain.model.Movie

data class ScreenListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val currentPage: Int = 1,
    val isAtEnd: Boolean = false,
    val error: String? = null,

    val searchString: String = "",
    val searchStringInProgress: Boolean = false,
)
