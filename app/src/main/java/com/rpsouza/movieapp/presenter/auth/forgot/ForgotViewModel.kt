package com.rpsouza.movieapp.presenter.auth.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.domain.remote.usecase.auth.ForgotUseCase
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
  private val forgotUseCase: ForgotUseCase
) : ViewModel() {

  fun forgot(email: String) = liveData(Dispatchers.IO) {
    try {
      emit(StateView.Loading())

      forgotUseCase.invoke(email)

      emit(StateView.Success(Unit))
    } catch (ex: Exception) {
      ex.printStackTrace()
      emit(StateView.Error(message = ex.message))
    }
  }
}