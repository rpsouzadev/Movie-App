package com.rpsouza.movieapp.presenter.main.bottomBar.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpsouza.movieapp.domain.model.gener.Genre
import com.rpsouza.movieapp.domain.remote.usecase.movie.GetGenreListUseCase
import com.rpsouza.movieapp.domain.remote.usecase.movie.GetMovieByGenreUseCase
import com.rpsouza.movieapp.presenter.model.MoviesByGenre
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenreListUseCase: GetGenreListUseCase,
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase
) : ViewModel() {
    private val _movieList = MutableLiveData<List<MoviesByGenre>>()
    val movieList: LiveData<List<MoviesByGenre>> get() = _movieList

    private val _homeState = MutableLiveData<StateView<Unit>>()
    val homeState: LiveData<StateView<Unit>> get() = _homeState

    init {
        getGenreList()
    }

    private fun getGenreList() {
        viewModelScope.launch {
            try {
                _homeState.postValue(StateView.Loading())

                val genreList = getGenreListUseCase()
                getMovieByGenreList(genreList)

            } catch (ex: Exception) {
                ex.printStackTrace()
                _homeState.postValue(StateView.Error(ex.message))
            }
        }
    }

    private fun getMovieByGenreList(genreList: List<Genre>) {
        val moviesByGenreList: MutableList<MoviesByGenre> = mutableListOf()

        viewModelScope.launch {
            genreList.forEach { genre ->
                try {
                    val moviesByGenre = getMovieByGenreUseCase(genreId = genre.id)
                    val movie = MoviesByGenre(
                        id = genre.id,
                        name = genre.name,
                        movies = moviesByGenre.take(7)
                    )

                    moviesByGenreList.add(movie)

                    if (moviesByGenreList.size == genreList.size) {
                        _movieList.postValue(moviesByGenreList)
                        _homeState.postValue(StateView.Success(Unit))
                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                    _homeState.postValue(StateView.Error(ex.message))
                }
            }
        }
    }

}