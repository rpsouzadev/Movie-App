package com.rpsouza.movieapp.presenter.main.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.domain.local.usecase.InsertMovieLocalUseCase
import com.rpsouza.movieapp.domain.model.favorite.FavoriteMovie
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.remote.usecase.cast.GetCreditsUseCase
import com.rpsouza.movieapp.domain.remote.usecase.favorite.GetFavoritesUseCase
import com.rpsouza.movieapp.domain.remote.usecase.favorite.SaveFavoritesUseCase
import com.rpsouza.movieapp.domain.remote.usecase.movie.GetMovieDetailsUseCase
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val insertMovieLocalUseCase: InsertMovieLocalUseCase,
    private val saveFavoritesUseCase: SaveFavoritesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _movieId = MutableLiveData(0)
    val movieId: LiveData<Int> = _movieId

    fun getMovieDetails(movieId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val movieDetails = getMovieDetailsUseCase.invoke(movieId = movieId)

            emit(StateView.Success(data = movieDetails))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

    fun getCredits(movieId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val castList = getCreditsUseCase.invoke(movieId = movieId)

            emit(StateView.Success(data = castList))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

    fun insertMovieLocal(movie: Movie) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            insertMovieLocalUseCase(movie)

            emit(StateView.Success(Unit))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

    fun setMovieId(movieId: Int) {
        _movieId.value = movieId
    }

    fun getFavorites() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val favorites = getFavoritesUseCase()

            emit(StateView.Success(favorites))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

    fun saveFavorites(favorites: List<FavoriteMovie>) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveFavoritesUseCase(favorites)

            emit(StateView.Success(Unit))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}