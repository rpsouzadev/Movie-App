package com.rpsouza.movieapp.presenter.main.bottomBar.profile.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.domain.remote.usecase.user.GetUserUseCase
import com.rpsouza.movieapp.domain.remote.usecase.user.UserUpdateUseCase
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userUpdateUseCase: UserUpdateUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _validateData = MutableLiveData<Pair<Boolean, Int?>>()
    val validateData: MutableLiveData<Pair<Boolean, Int?>> = _validateData

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

    fun getUser() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            val user = getUserUseCase()
            emit(StateView.Success(user))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

    fun validateData(
        firstName: String,
        lastName: String,
        phone: String,
        gender: String,
        country: String
    ) {
        if (firstName.isEmpty()) {
            _validateData.value = Pair(false, R.string.text_first_name_empty_edit_profile_fragment)
            return
        }

        if (lastName.isEmpty()) {
            _validateData.value = Pair(false, R.string.text_last_name_empty_edit_profile_fragment)
            return
        }

        if (phone.isEmpty()) {
            _validateData.value = Pair(false, R.string.text_phone_empty_edit_profile_fragment)
            return
        }

        if (gender.isEmpty()) {
            _validateData.value = Pair(false, R.string.text_gender_empty_edit_profile_fragment)
            return
        }

        if (country.isEmpty()) {
            _validateData.value = Pair(false, R.string.text_country_empty_edit_profile_fragment)
            return
        }

        _validateData.value = Pair(true, null)
    }

}