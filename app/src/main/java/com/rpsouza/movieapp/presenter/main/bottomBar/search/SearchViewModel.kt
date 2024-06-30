package com.rpsouza.movieapp.presenter.main.bottomBar.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.usecase.movie.SearchMoviesUseCase
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get() = _movieList

    private val _searchState = MutableLiveData<StateView<Unit>>()
    val searchState: LiveData<StateView<Unit>> get() = _searchState

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _searchState.postValue(StateView.Loading())

                val movies = searchMoviesUseCase(query = query)

//                _movieList.postValue(movies)
                _searchState.postValue(StateView.Success(Unit))
            } catch (ex: HttpException) {
                ex.printStackTrace()
                _searchState.postValue(StateView.Error(message = ex.message))
            } catch (ex: Exception) {
                ex.printStackTrace()
                _searchState.postValue(StateView.Error(message = ex.message))
            }
        }
    }
}