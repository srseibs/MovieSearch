package com.sailinghawklabs.moviesearch.di

import com.sailinghawklabs.moviesearch.data.remote.MovieApi
import com.sailinghawklabs.moviesearch.data.remote.repository.MovieRepositoryImpl
import com.sailinghawklabs.moviesearch.domain.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository =
        MovieRepositoryImpl(api)



}