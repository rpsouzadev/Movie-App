package com.rpsouza.movieapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rpsouza.movieapp.data.local.dao.MovieDao
import com.rpsouza.movieapp.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}