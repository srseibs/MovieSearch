package com.sailinghawklabs.moviesearch.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sailinghawklabs.moviesearch.BuildConfig
import com.sailinghawklabs.moviesearch.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(loggingInterceptor)
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(
        httpClient: OkHttpClient,
        gson: Gson,
    ) = Retrofit.Builder()
        .baseUrl(MovieApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()
        .create(MovieApi::class.java)

}