package com.example.pokeme.repository

import android.util.Log
import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserRepository {
    companion object {
        val instance = UserRepository()
        private const val DEBUG_CODE: String = "USER_REPO"
    }
    private val authService: FirebaseAuth = FirebaseAuth.getInstance()

    val user: FirebaseUser?
        get() = authService.currentUser

    fun register(email: String, password: String, callback: OnDataReadyCallback) {
        val task = authService.createUserWithEmailAndPassword(email, password)
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
        val task = authService.signInWithEmailAndPassword(email, password)
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

    fun logout() {
        authService.signOut()
    }
}
