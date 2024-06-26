package com.rpsouza.movieapp.data.remote.repository.movie

import androidx.paging.PagingSource
import com.rpsouza.movieapp.data.api.ServiceAPI
import com.rpsouza.movieapp.data.paging.MovieByGenrePagingSource
import com.rpsouza.movieapp.data.paging.SearchMoviePagingSource
import com.rpsouza.movieapp.data.remote.model.basePagination.BasePaginationResponse
import com.rpsouza.movieapp.data.remote.model.gener.GenreListResponse
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
) : MovieRepository {
    override suspend fun getGenreList(): GenreListResponse {
        return serviceAPI.getGenreList()
    }

    override fun getMovieByGenrePagination(genreId: Int?): PagingSource<Int, MovieResponse> {
        return MovieByGenrePagingSource(serviceAPI, genreId)
    }

    override suspend fun getMovieByGenre(genreId: Int?): BasePaginationResponse<List<MovieResponse>> {
        return serviceAPI.getMovieByGenre(genreId)
    }

    override fun searchMovies(query: String): PagingSource<Int, MovieResponse> {
        return SearchMoviePagingSource(serviceAPI, query)
    }
}