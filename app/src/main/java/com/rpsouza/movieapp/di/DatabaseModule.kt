package com.rpsouza.movieapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rpsouza.movieapp.data.local.dao.MovieDao
import com.rpsouza.movieapp.data.local.db.AppDatabase
import com.rpsouza.movieapp.utils.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        Database.MOVIE_DATABASE
    ).build()

    @Provides
    fun providesMovieDao(database: AppDatabase): MovieDao = database.movieDao()
}