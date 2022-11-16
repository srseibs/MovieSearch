package com.sailinghawklabs.moviesearch.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sailinghawklabs.moviesearch.domain.model.Movie
import com.sailinghawklabs.moviesearch.screen.ScreenListEvent.*
import com.sailinghawklabs.moviesearch.ui.theme.MovieSearchTheme
import com.sailinghawklabs.moviesearch.util.fakeMovies
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val state = viewModel.state


    val snackScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun showSnackBar(message: String) {
        snackScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Dismiss"
            )
            viewModel.onEvent(ClearError)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },

        ) {
        if (state.error != null) {
            showSnackBar(state.error)
        }

        MovieSearchScreenContent(
            modifier = modifier.padding(it),
            movies = state.movies,
            isLoading = state.isLoading,
            endIsReached = state.isAtEnd,
            stringInProgress = state.searchStringInProgress,
            onEvent = viewModel::onEvent,
            loadMoreMovies = { viewModel.loadNextItems() },
            searchString = state.searchString,
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MovieSearchScreenContent(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    isLoading: Boolean = false,
    endIsReached: Boolean = false,
    loadMoreMovies: () -> Unit,
    stringInProgress: Boolean = true,
    searchString: String = "Friends",
    onEvent: (ScreenListEvent) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxWidth()
    ) {


        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    when{
                        focusState.hasFocus || focusState.isFocused -> {
                            onEvent(SearchChanged(searchString))
                        }
                    }
                },
            colors = textFieldColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                textColor = MaterialTheme.colorScheme.onTertiaryContainer,
            ),
            value = searchString,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onEvent(PerformSearch)
                }
            ),
            onValueChange = { onEvent(SearchChanged(it)) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                Row {

                    if (stringInProgress) {
                        IconButton(
                            onClick = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                onEvent(PerformSearch)
                            }
                        ) {
                            Icon(
                                Icons.Default.Check, contentDescription = ""
                            )
                        }
                        IconButton(onClick = {
                            onEvent(ClearSearch)
                        }) {
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

            if(movies.isEmpty()) {
                Text(
                    modifier = Modifier.padding(32.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    text = "No movies found.\nPlease search for some."
                )
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
            onEvent = {}
        )
    }
}