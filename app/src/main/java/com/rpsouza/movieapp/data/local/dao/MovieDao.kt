package com.rpsouza.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpsouza.movieapp.data.local.entity.MovieEntity
import com.rpsouza.movieapp.utils.Columns
import com.rpsouza.movieapp.utils.Tables
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${Tables.MOVIE_TABLE}")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM ${Tables.MOVIE_TABLE} WHERE ${Columns.MOVIE_ID_COLUMN} = :movieId")
    fun deleteMovie(movieId: Int?)
}