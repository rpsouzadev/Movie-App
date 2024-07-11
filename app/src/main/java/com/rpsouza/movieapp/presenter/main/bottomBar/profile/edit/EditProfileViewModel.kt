package com.rpsouza.movieapp.presenter.main.bottomBar.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.domain.remote.usecase.user.UserUpdateUseCase
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userUpdateUseCase: UserUpdateUseCase
) : ViewModel() {

    fun updateUser(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            userUpdateUseCase(user)

            emit(StateView.Success(Unit))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

}