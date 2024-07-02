package com.rpsouza.movieapp.presenter.main.movieGenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.usecase.movie.GetMovieByGenrePaginationUseCase
import com.rpsouza.movieapp.domain.remote.usecase.movie.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val getMovieByGenrePaginationUseCase: GetMovieByGenrePaginationUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _movieList = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movieList get() = _movieList.asStateFlow()

    private var currentGenreId: Int? = null

    fun getMovieByGenrePaginationList(genreId: Int?, forceRequest: Boolean) = viewModelScope.launch {
        if (currentGenreId != genreId || forceRequest) {
            currentGenreId = genreId
            getMovieByGenrePaginationUseCase(genreId = genreId).cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                _movieList.emit(pagingData)
            }
        }
    }

    fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return searchMoviesUseCase(query = query).cachedIn(viewModelScope)
    }
}