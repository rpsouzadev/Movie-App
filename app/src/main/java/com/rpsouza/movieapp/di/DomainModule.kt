package com.rpsouza.movieapp.di

import com.rpsouza.movieapp.data.repository.auth.FirebaseAuthenticationImpl
import com.rpsouza.movieapp.data.repository.movie.MovieRepositoryImpl
import com.rpsouza.movieapp.domain.repository.auth.FirebaseAuthentication
import com.rpsouza.movieapp.domain.repository.movie.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

  @Binds
  abstract fun bindsFirebaseAuthentication(
    firebaseAuthenticationImpl: FirebaseAuthenticationImpl
  ): FirebaseAuthentication

  @Binds
  abstract fun bindsMovieRepositoryImpl(
    movieRepositoryImpl: MovieRepositoryImpl
  ): MovieRepository
}