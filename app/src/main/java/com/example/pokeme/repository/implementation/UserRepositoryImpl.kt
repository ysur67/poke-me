package com.example.pokeme.repository

import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class UserRepositoryFirebase @Inject constructor(
    val authService: FirebaseAuth
    ) : UserRepository {

    override val user: FirebaseUser?
        get() = authService.currentUser

    override fun register(
        email: String,
        password: String,
        callback: (Result<FirebaseUser>) -> Unit
    ) {
        val task = authService.createUserWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            val user = it.user
            if (user != null) {
                callback(Result.Success(user))
            }
        }
        task.addOnFailureListener {
            callback(Result.Error(it))
        }
    }

    override fun login(
        email: String,
        password: String,
        callback: (Result<FirebaseUser>) -> Unit
    ) {
        val task = authService.signInWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            val user = it.user
            if (user != null) {
                callback(Result.Success(user))
            }
        }
        task.addOnFailureListener {
            callback(Result.Error(it))
        }
    }

    override fun logout() {
        authService.signOut()
    }

    companion object {
        private const val DEBUG_CODE: String = "USER_REPO"
    }
}
