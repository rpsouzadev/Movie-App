package com.rpsouza.movieapp.presenter.main.movieGenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.BuildConfig
import com.rpsouza.movieapp.domain.usecase.movie.GetMovieByGenreUseCase
import com.rpsouza.movieapp.domain.usecase.movie.SearchMoviesUseCase
import com.rpsouza.movieapp.utils.Constants
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
  private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
  private val searchMoviesUseCase: SearchMoviesUseCase
): ViewModel() {

  fun getMovieByGenreList(genreId: Int?) = liveData(Dispatchers.IO) {
    try {
      emit(StateView.Loading())

      val moviesByGenre = getMovieByGenreUseCase.invoke(
        apiKey = BuildConfig.THE_MOVIE_DB_KEY,
        language = Constants.Movie.LANGUAGE,
        genreId = genreId
      )

      emit(StateView.Success(data = moviesByGenre))
    } catch (ex: HttpException) {
      ex.printStackTrace()
      emit(StateView.Error(message = ex.message))
    } catch (ex: Exception) {
      ex.printStackTrace()
      emit(StateView.Error(message = ex.message))
    }
  }

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