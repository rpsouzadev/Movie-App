package com.rpsouza.movieapp.presenter.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.domain.remote.usecase.auth.RegisterUseCase
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
  private val registerUseCase: RegisterUseCase
) : ViewModel() {

  fun register(email: String, password: String) = liveData(Dispatchers.IO) {
    try {
      emit(StateView.Loading())

      registerUseCase.invoke(email, password)

      emit(StateView.Success(Unit))
    } catch (ex: Exception) {
      ex.printStackTrace()
      emit(StateView.Error(message = ex.message))
    }
  }
}