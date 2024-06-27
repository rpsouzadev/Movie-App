package com.rpsouza.movieapp.presenter.main.movieGenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.PagingData
import com.rpsouza.movieapp.BuildConfig
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.usecase.movie.GetMovieByGenreUseCase
import com.rpsouza.movieapp.domain.remote.usecase.movie.SearchMoviesUseCase
import com.rpsouza.movieapp.utils.Constants
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _movieList = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    private val movieList get() = _movieList.asStateFlow()

    private var currentGenreId: Int? = null

    fun getMovieByGenreList(genreId: Int?, forceRequest: Boolean = false) {
        if (currentGenreId != genreId || forceRequest) {
            currentGenreId = genreId
            getMovieByGenreUseCase(
                apiKey = BuildConfig.THE_MOVIE_DB_KEY,
                language = Constants.Movie.LANGUAGE,
                genreId = genreId
            )
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