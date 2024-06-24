package com.rpsouza.movieapp.domain.local.repository

import androidx.room.Query
import com.rpsouza.movieapp.data.local.entity.MovieEntity
import com.rpsouza.movieapp.utils.Columns
import com.rpsouza.movieapp.utils.Tables
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {
    fun getMovies(): Flow<List<MovieEntity>>

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun deleteMovie(movieId: Int?)
}