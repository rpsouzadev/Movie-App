package com.rpsouza.movieapp.presenter.main.bottomBar.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.domain.remote.usecase.favorite.GetFavoritesUseCase
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
): ViewModel() {

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
}