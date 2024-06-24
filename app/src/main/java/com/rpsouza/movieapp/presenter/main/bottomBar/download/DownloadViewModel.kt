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

    fun getMovies() = viewModelScope.launch {
        getMoviesLocalUseCase().collect { movies ->
            _movieList.postValue(movies)
        }
    }

    fun deleteMovie(movieId: Int?) = viewModelScope.launch {
        deleteMovieLocalUseCase(movieId)

        val movieCurrentList = _movieList.value
        val newList = movieCurrentList?.filter { movie ->
            movie.id != movieId
        }

        _movieList.postValue(newList)
    }
}
