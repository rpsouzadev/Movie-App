package com.rpsouza.movieapp.presenter.main.bottomBar.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.usecase.movie.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {
    private val _searchResults = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchResults: StateFlow<PagingData<Movie>> get() = _searchResults

    fun searchMovies(query: String) {
        viewModelScope.launch {
            searchMoviesUseCase(query = query).cachedIn(viewModelScope)
                .collectLatest { _searchResults.emit(it) }
        }
    }
}
