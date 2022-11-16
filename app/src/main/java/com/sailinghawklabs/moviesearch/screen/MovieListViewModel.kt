package com.sailinghawklabs.moviesearch.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.htmlEncode
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.moviesearch.domain.MovieRepository
import com.sailinghawklabs.moviesearch.screen.ScreenListEvent.ClearError
import com.sailinghawklabs.moviesearch.screen.ScreenListEvent.ClearSearch
import com.sailinghawklabs.moviesearch.screen.ScreenListEvent.LoadMoreMovies
import com.sailinghawklabs.moviesearch.screen.ScreenListEvent.PerformSearch
import com.sailinghawklabs.moviesearch.screen.ScreenListEvent.SearchChanged
import com.sailinghawklabs.moviesearch.util.paginator.DefaultPaginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    var state by mutableStateOf(ScreenListState())
        private set

    private val paginator = DefaultPaginator(
        initialKey = state.currentPage,

        isLoading = {
            state = state.copy(
                isLoading = it
            )
        },
        requestPage = { page ->
            repository.searchMovies(
                query = state.searchString.trim().htmlEncode(),
                page = page
            )
        },
        getNextKey = {
            state.currentPage + 1
        },
        onError = {
            state = state.copy(
                error = it?.localizedMessage,
                isAtEnd = true,
                isLoading = false,
            )
        },
        onSuccess = { items, newPage ->
            state = state.copy(
                movies = state.movies + items,
                currentPage = newPage,
                isAtEnd = items.isEmpty()
            )
        }
    )

    fun onEvent(event: ScreenListEvent) {
        when (event) {
            ClearError -> {
                state = state.copy(
                    error = null
                )
            }

            ClearSearch -> {
                state = state.copy(
                    searchString = "",
                    searchStringInProgress = false,
                )
            }

            LoadMoreMovies -> {
                loadNextItems()
            }

            PerformSearch -> {
                performSearch()
            }

            is SearchChanged -> {
                updateSearchString(event.searchString)
            }
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun updateSearchString(str:String) {
        state = state.copy(
            searchString = str,
            searchStringInProgress = true,
        )
    }

    fun performSearch() {
        state = state.copy(
            searchStringInProgress = false,
            movies = emptyList()
        )
        paginator.reset()

        loadNextItems()
    }



}