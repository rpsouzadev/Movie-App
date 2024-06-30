package com.rpsouza.movieapp.domain.remote.repository.movie

import androidx.paging.PagingSource
import com.rpsouza.movieapp.data.remote.model.cast.CastResponse
import com.rpsouza.movieapp.data.remote.model.credits.CreditsResponse
import com.rpsouza.movieapp.data.remote.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRepository {

    suspend fun getGenreList(): GenreListResponse

    fun getMovieByGenre(genreId: Int?): PagingSource<Int, MovieResponse>

    fun searchMovies(query: String): PagingSource<Int, MovieResponse>
}