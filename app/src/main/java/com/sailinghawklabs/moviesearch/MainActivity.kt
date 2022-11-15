package com.sailinghawklabs.moviesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sailinghawklabs.moviesearch.screen.MovieSearchScreen
import com.sailinghawklabs.moviesearch.ui.theme.MovieSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieSearchTheme {
                MovieSearchScreen()
            }
        }
    }
}

