package com.sailinghawklabs.moviesearch.data.remote.response

import com.sailinghawklabs.moviesearch.domain.model.Movie

fun Search.toMovie() = Movie(
    title = this.Title,
    posterUrl = this.Poster,
    dateReleased = this.Year,
    imbdId = this.imdbID,
    type = this.Type

)