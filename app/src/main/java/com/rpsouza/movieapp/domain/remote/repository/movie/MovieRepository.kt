package com.rpsouza.movieapp.domain.remote.repository.movie

import androidx.paging.PagingSource
import com.rpsouza.movieapp.data.remote.model.cast.CastResponse
import com.rpsouza.movieapp.data.remote.model.credits.CreditsResponse
import com.rpsouza.movieapp.data.remote.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRepository {

    suspend fun getGenreList(apiKey: String, language: String?): GenreListResponse

    fun getMovieByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): PagingSource<Int, MovieResponse>

    fun searchMovies(
        apiKey: String,
        language: String?,
        query: String
    ): PagingSource<Int, MovieResponse>
}