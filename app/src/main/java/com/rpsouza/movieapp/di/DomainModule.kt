package com.rpsouza.movieapp.di

import com.rpsouza.movieapp.data.local.repository.MovieLocalRepositoryImpl
import com.rpsouza.movieapp.data.remote.repository.auth.FirebaseAuthenticationImpl
import com.rpsouza.movieapp.data.remote.repository.favorite.FavoriteMovieRepositoryImpl
import com.rpsouza.movieapp.data.remote.repository.movie.MovieDetailsRepositoryImpl
import com.rpsouza.movieapp.data.remote.repository.movie.MovieRepositoryImpl
import com.rpsouza.movieapp.data.remote.repository.user.UserRepositoryImpl
import com.rpsouza.movieapp.domain.local.repository.MovieLocalRepository
import com.rpsouza.movieapp.domain.remote.repository.auth.FirebaseAuthentication
import com.rpsouza.movieapp.domain.remote.repository.movie.FavoriteMovieRepository
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieDetailsRepository
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import com.rpsouza.movieapp.domain.remote.repository.user.UserRepository
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

    @Binds
    abstract fun bindsMovieDetailsRepositoryImpl(
        movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository

    @Binds
    abstract fun bindsMovieLocalRepositoryImpl(
        movieLocalRepositoryImpl: MovieLocalRepositoryImpl
    ): MovieLocalRepository

    @Binds
    abstract fun bindsUserRepositoryImpl(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindsFavoriteMovieRepositoryImpl(
        favoriteMovieRepositoryImpl: FavoriteMovieRepositoryImpl
    ): FavoriteMovieRepository
}