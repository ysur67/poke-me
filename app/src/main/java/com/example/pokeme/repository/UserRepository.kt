package com.example.pokeme.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class UserRepository {
    companion object {
        val instance = UserRepository()
        private const val DEBUG_CODE: String = "USER_REPO"
    }
    private val connection: FirebaseAuth = FirebaseAuth.getInstance()
    val user: FirebaseUser?
        get() = connection.currentUser

    fun register(email: String, password: String, callback: OnDataReadyCallback) {
        val task = connection.createUserWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            val user = it.user
            if (user != null) {
                callback.onDataReady(Result.Success(user))
            }
        }
        task.addOnFailureListener {
            callback.onDataReady(Result.Error(it))
        }
    }

    fun login(email: String, password: String, callback: OnDataReadyCallback){
        val task = connection.signInWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            val user = it.user
            if (user != null) {
                callback.onDataReady(Result.Success(user))
            }
        }
        task.addOnFailureListener {
            callback.onDataReady(Result.Error(it))
        }
    }
}
