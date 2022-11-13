package com.sailinghawklabs.moviesearch.data.remote.response

data class MovieSearchDto(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)