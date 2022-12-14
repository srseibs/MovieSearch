package com.sailinghawklabs.moviesearch.data.remote

import com.sailinghawklabs.moviesearch.BuildConfig
import com.sailinghawklabs.moviesearch.data.remote.response.MovieSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    companion object {
        const val BASE_URL = "https://www.omdbapi.com/"
    }

    // https://www.omdbapi.com/?apikey=11112234&s=Star

    @GET(".") // "." used if there are no additions to BASE_URL
    suspend fun searchMovies(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
        @Query("s") queryString: String,
        @Query("page") page:Int = 1,
    ): MovieSearchDto


}