package com.rpsouza.movieapp.data.remote.repository.user

import android.net.Uri
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.domain.remote.repository.user.UserRepository
import com.rpsouza.movieapp.utils.FirebaseHelper
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl @Inject constructor(
    firebaseDataBase: FirebaseDatabase,
    firebaseStorage: FirebaseStorage
) : UserRepository {
    private val profileRef = firebaseDataBase.reference.child("profile")

    private val storageRef = firebaseStorage.reference
        .child("images")
        .child("profiles")
        .child(FirebaseHelper.getUserId())
        .child("image_profile.jpeg")

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

    override suspend fun saveUserImage(uri: Uri): String {
        return suspendCoroutine { continuation ->
            val uploadTask = storageRef.putFile(uri)

            uploadTask.addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                Log.d("INFOTEST", "Upload is $progress% done")
            }.addOnSuccessListener {
                storageRef.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        continuation.resumeWith(Result.success(downloadUri.toString()))
                    } else {
                        task.exception?.let { exception ->
                            continuation.resumeWith(Result.failure(exception))
                        }
                    }
                }
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }

        }
    }
}