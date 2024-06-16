package com.rpsouza.movieapp.presenter.main.bottomBar.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.BuildConfig
import com.rpsouza.movieapp.domain.usecase.movie.SearchMoviesUseCase
import com.rpsouza.movieapp.utils.Constants
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    fun searchMovies(query: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movieList = searchMoviesUseCase.invoke(
                apiKey = BuildConfig.THE_MOVIE_DB_KEY,
                language = Constants.Movie.LANGUAGE,
                query = query
            )

            emit(StateView.Success(data = movieList))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}