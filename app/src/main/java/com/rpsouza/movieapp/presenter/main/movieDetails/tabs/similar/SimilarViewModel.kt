package com.rpsouza.movieapp.presenter.main.movieDetails.tabs.similar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.BuildConfig
import com.rpsouza.movieapp.domain.remote.usecase.movie.GetMoviesSimilarUseCase
import com.rpsouza.movieapp.utils.Constants
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(
    private val getMoviesSimilarUseCase: GetMoviesSimilarUseCase
) : ViewModel() {

    fun getSimilar(movieId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val moviesSimilar = getMoviesSimilarUseCase.invoke(
                apiKey = BuildConfig.THE_MOVIE_DB_KEY,
                language = Constants.Movie.LANGUAGE,
                movieId = movieId
            )

            emit(StateView.Success(data = moviesSimilar))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}