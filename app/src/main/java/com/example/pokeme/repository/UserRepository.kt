package com.example.pokeme.repository

import android.util.Log
import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

interface UserRepository {
    val user: FirebaseUser?
    fun register(email: String, password: String, callback: (Result<FirebaseUser>) -> Unit)
    fun login(email: String, password: String, callback: (Result<FirebaseUser>) -> Unit)
    fun logout()
}


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
