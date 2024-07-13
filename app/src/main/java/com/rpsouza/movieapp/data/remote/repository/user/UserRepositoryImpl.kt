package com.rpsouza.movieapp.data.remote.repository.user

import com.google.firebase.database.FirebaseDatabase
import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.domain.remote.repository.user.UserRepository
import com.rpsouza.movieapp.utils.FirebaseHelper
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl @Inject constructor(
    private val firebaseDataBase: FirebaseDatabase
) : UserRepository {
    private val profileRef = firebaseDataBase.reference.child("profile")

    override suspend fun updateUser(user: User) {
        return suspendCoroutine { continuation ->
            profileRef.child(FirebaseHelper.getUserId()).setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        task.exception?.let { exception ->
                            continuation.resumeWith(Result.failure(exception))
                        }
                    }
                }
        }
    }

    override suspend fun getUser(): User {
        return suspendCoroutine { continuation ->
            profileRef.child(FirebaseHelper.getUserId())
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val snapshot = task.result
                        val user = snapshot.getValue(User::class.java)
                        user?.let { continuation.resumeWith(Result.success(it)) }
                    } else {
                        task.exception?.let { exception ->
                            continuation.resumeWith(Result.failure(exception))
                        }
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}