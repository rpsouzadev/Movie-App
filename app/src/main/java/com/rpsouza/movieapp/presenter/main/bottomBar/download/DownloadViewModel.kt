package com.rpsouza.movieapp.presenter.main.bottomBar.download

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpsouza.movieapp.domain.local.usecase.DeleteMovieLocalUseCase
import com.rpsouza.movieapp.domain.local.usecase.GetMoviesLocalUseCase
import com.rpsouza.movieapp.domain.model.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val getMoviesLocalUseCase: GetMoviesLocalUseCase,
    private val deleteMovieLocalUseCase: DeleteMovieLocalUseCase
) : ViewModel() {
    private val _movieList = MutableLiveData(listOf<Movie>())
    val movieList: LiveData<List<Movie>> = _movieList

    private val _movieSearchList = MutableLiveData(listOf<Movie>())
    val movieSearchList: LiveData<List<Movie>> = _movieSearchList

    fun getMovies() = viewModelScope.launch {
        getMoviesLocalUseCase().collect { movies ->
            _movieList.postValue(movies)
        }
    }

    fun deleteMovie(movieId: Int?) = viewModelScope.launch {
        deleteMovieLocalUseCase(movieId)

        val newList = _movieList.value?.filter { movie ->
            movie.id != movieId
        }

        _movieList.postValue(newList)
    }

    fun searchMovie(search: String) = viewModelScope.launch {
        val newList = _movieList.value?.filter { movie ->
            movie.title?.contains(search, true) == true
        }

        _movieSearchList.postValue(newList)
    }
}
