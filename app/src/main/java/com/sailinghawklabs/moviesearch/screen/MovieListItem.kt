package com.sailinghawklabs.moviesearch.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
        ) {

            AsyncImage(
                modifier = Modifier.fillMaxWidth(0.4f),
                model = movie.posterUrl,
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "poster"
            )

            Column(
                modifier = modifier.wrapContentHeight().fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = movie.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentHeight(),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = "${movie.dateReleased} / ${movie.type}",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
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