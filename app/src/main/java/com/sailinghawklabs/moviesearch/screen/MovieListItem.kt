package com.sailinghawklabs.moviesearch.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sailinghawklabs.moviesearch.R
import com.sailinghawklabs.moviesearch.domain.model.Movie
import com.sailinghawklabs.moviesearch.ui.theme.MovieSearchTheme
import com.sailinghawklabs.moviesearch.util.fakeMovies

@Composable
fun MovieListItem(
    movie: Movie,
    modifier: Modifier = Modifier,
) {

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = movie.title,
                modifier = Modifier.wrapContentHeight(),
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = "${movie.dateReleased} / ${movie.type}",
                style = MaterialTheme.typography.labelLarge,
            )
            AsyncImage(
                alignment = Center,
                modifier = Modifier.height(200.dp),
                model = movie.posterUrl,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "poster")

        }
    }
}


@Preview
@Composable
fun MovieListItemPreview() {
    MovieSearchTheme() {

        MovieListItem(movie = fakeMovies[0])

    }
}