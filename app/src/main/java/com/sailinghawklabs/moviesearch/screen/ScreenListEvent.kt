package com.sailinghawklabs.moviesearch.screen

sealed class ScreenListEvent {
    object LoadMoreMovies: ScreenListEvent()
    data class SearchChanged(val searchString: String): ScreenListEvent()
    object PerformSearch: ScreenListEvent()
    object ClearSearch: ScreenListEvent()
    object ClearError: ScreenListEvent()
}
