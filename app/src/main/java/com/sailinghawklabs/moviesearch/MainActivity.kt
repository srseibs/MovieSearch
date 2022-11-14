package com.sailinghawklabs.moviesearch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.sailinghawklabs.moviesearch.data.remote.MovieApi
import com.sailinghawklabs.moviesearch.ui.theme.MovieSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var api: MovieApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val result = api.searchMovies(queryString = "Star")
            Log.d("MainActivity", "onCreate: result = $result")
        }

        setContent {
            MovieSearchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text("This is Display Large", style = MaterialTheme.typography.displayLarge)
                        Text("This is Display Medium", style = MaterialTheme.typography.displayMedium)
                        Text("This is Display Small", style = MaterialTheme.typography.displaySmall)
                        Text("This is Headline Large", style = MaterialTheme.typography.headlineLarge)
                        Text("This is Headline Medium", style = MaterialTheme.typography.headlineMedium)
                        Text("This is Headline Small", style = MaterialTheme.typography.headlineSmall)
                        Text("This is Body Medium", style = MaterialTheme.typography.bodyMedium)
                        Text("This is Body Medium Italic", style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic )
                    }
                }
            }
        }
    }
}
