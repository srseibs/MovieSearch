package com.sailinghawklabs.moviesearch.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailinghawklabs.moviesearch.domain.MovieRepository
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
            repository.searchMovies(query = state.searchString, page = page)
        },
        getNextKey = {
            state.currentPage + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newPage ->
            state = state.copy(
                movies = state.movies + items,
                currentPage = newPage,
                isAtEnd = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun initializeSearch() {
        state = state.copy(
            searchString = "",
            searchStringInProgress = false,
        )
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
        )
        paginator.reset()
        loadNextItems()
    }

}