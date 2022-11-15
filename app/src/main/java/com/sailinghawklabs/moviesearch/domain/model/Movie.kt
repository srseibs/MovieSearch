package com.sailinghawklabs.moviesearch.domain.model

data class Movie(
    val title: String,
    val posterUrl: String?,
    val dateReleased: String,
    val imbdId: String,
    val type: String,
)
