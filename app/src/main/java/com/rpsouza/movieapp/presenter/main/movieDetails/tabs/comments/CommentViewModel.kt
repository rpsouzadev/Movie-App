package com.rpsouza.movieapp.presenter.main.movieDetails.tabs.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.movieapp.domain.remote.usecase.review.GetReviewsUseCase
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val getReviewsUseCase: GetReviewsUseCase
): ViewModel() {

    fun getReviews(movieId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val comments = getReviewsUseCase.invoke(movieId = movieId)

            emit(StateView.Success(data = comments))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}