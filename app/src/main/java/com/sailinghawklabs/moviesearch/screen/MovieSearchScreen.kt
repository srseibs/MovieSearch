package com.sailinghawklabs.moviesearch.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sailinghawklabs.moviesearch.domain.model.Movie
import com.sailinghawklabs.moviesearch.ui.theme.MovieSearchTheme
import com.sailinghawklabs.moviesearch.util.fakeMovies

@Composable
fun MovieSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    MovieSearchScreenContent(
        modifier = modifier,
        movies = state.movies,
        isLoading = state.isLoading,
        endIsReached = state.isAtEnd,
        stringInProgress = state.searchStringInProgress,
        loadMoreMovies = { viewModel.loadNextItems() },
        searchString = state.searchString,
        onSearchChanged = { viewModel.updateSearchString(it) },
        doSearch = { viewModel.performSearch() },
        clearSearch = { viewModel.initializeSearch() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreenContent(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    isLoading: Boolean = false,
    endIsReached: Boolean = false,
    loadMoreMovies: () -> Unit,
    stringInProgress: Boolean = true,
    searchString: String = "Friends",
    onSearchChanged: (String) -> Unit,
    doSearch: () -> Unit,
    clearSearch: () -> Unit,

) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                textColor = MaterialTheme.colorScheme.onTertiaryContainer,
            ),
            value = searchString,
            onValueChange = { onSearchChanged(it) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                Row {

                    if (stringInProgress) {
                        IconButton(onClick = { doSearch() }) {
                            Icon(
                                Icons.Default.Check, contentDescription = ""
                            )
                        }
                        IconButton(onClick = { clearSearch() }) {
                            Icon(
                                Icons.Default.Close, contentDescription = ""
                            )
                        }
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies.size) { i ->
                    val movie = movies[i]
                    if (i >= movies.size - 1 && !isLoading && !endIsReached) {
                        loadMoreMovies()
                    }
                    MovieListItem(movie)
                }
            }

            if (isLoading) {
                CircularProgressIndicator()
            }
        }


    }


}


@Preview
@Composable
fun MovieSearchScreenContentPreview() {
    MovieSearchTheme() {
        MovieSearchScreenContent(
            movies = fakeMovies,
            loadMoreMovies = {},
            isLoading = false,
            onSearchChanged = {},
            doSearch = {},
            clearSearch = {},
        )
    }
}