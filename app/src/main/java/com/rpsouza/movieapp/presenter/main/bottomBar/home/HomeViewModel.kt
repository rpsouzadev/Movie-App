package com.rpsouza.movieapp.presenter.main.bottomBar.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.data.mapper.toPresentation
import com.rpsouza.movieapp.domain.remote.usecase.movie.GetGenreListUseCase
import com.rpsouza.movieapp.domain.remote.usecase.movie.GetMovieByGenreUseCase
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenreListUseCase: GetGenreListUseCase,
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase
) : ViewModel() {

  fun getGenreList() = liveData(Dispatchers.IO) {
    try {
      emit(StateView.Loading())

      val genreList = getGenreListUseCase.invoke( ).map { it.toPresentation() }

      emit(StateView.Success(data = genreList))
    } catch (ex: HttpException) {
      ex.printStackTrace()
      emit(StateView.Error(message = ex.message))
    } catch (ex: Exception) {
      ex.printStackTrace()
      emit(StateView.Error(message = ex.message))
    }
  }

  fun getMovieByGenreList(genreId: Int?) = liveData(Dispatchers.IO) {
    try {
      emit(StateView.Loading())

      val moviesByGenre = getMovieByGenreUseCase( genreId = genreId)

      emit(StateView.Success(data = moviesByGenre))
    } catch (ex: HttpException) {
      ex.printStackTrace()
      emit(StateView.Error(message = ex.message))
    } catch (ex: Exception) {
      ex.printStackTrace()
      emit(StateView.Error(message = ex.message))
    }
  }
}