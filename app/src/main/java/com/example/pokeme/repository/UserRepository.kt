package com.example.pokeme.repository

import com.example.pokeme.data.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class UserRepository : IRepository {
    companion object {
        val instance = UserRepository()
        private const val DEBUG_CODE: String = "USER_REPO"
    }

    private val connection: FirebaseAuth = FirebaseAuth.getInstance()

    override fun init() {
    }

    fun register(email: String, password: String, callback: OnDataReadyCallback) {
        val task = connection.createUserWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            val user = getUser(it)
            callback.onDataReady(Result.Success(user))
        }
        task.addOnFailureListener {
            callback.onDataReady(Result.Error(it))
        }
    }

    fun login(email: String, password: String, callback: OnDataReadyCallback){
        val task = connection.signInWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            val user = getUser(it)
            callback.onDataReady(Result.Success(user))
        }
        task.addOnFailureListener {
            callback.onDataReady(Result.Error(it))
        }
    }

    fun isUserExists(email: String) : Boolean {
        connection
        return true

    }

    private fun getUser(result: AuthResult) : User {
        return User(
            result.user?.email ?: "No data",
            result.user?.uid ?: "No data",
            result.user?.email ?: "No data"
        )
    }

}
