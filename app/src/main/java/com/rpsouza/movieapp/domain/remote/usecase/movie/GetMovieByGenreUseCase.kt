package com.rpsouza.movieapp.domain.remote.usecase.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rpsouza.movieapp.data.mapper.toDomain
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.repository.movie.MovieRepository
import com.rpsouza.movieapp.utils.Constants.Paging.DEFAULT_PAGE_INDEX
import com.rpsouza.movieapp.utils.Constants.Paging.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(genreId: Int?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = DEFAULT_PAGE_INDEX
            ),
            pagingSourceFactory = { movieRepository.getMovieByGenre(genreId) }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toDomain()
            }
        }
    }
}