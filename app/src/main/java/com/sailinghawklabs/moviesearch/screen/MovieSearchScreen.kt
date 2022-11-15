package com.sailinghawklabs.moviesearch.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    viewModel: MovieListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.state
    MovieSearchScreenContent(
        movies = state.movies,
        isLoading = state.isLoading,
    )
}


@Composable
fun MovieSearchScreenContent(
    movies: List<Movie>,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(movies) { movie ->
                MovieListItem(movie)
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }


    
}


@Preview
@Composable
fun MovieSearchScreenContentPreview() {
    
    MovieSearchTheme() {

        MovieSearchScreenContent(movies = fakeMovies, isLoading = false)
        
    }
    
}