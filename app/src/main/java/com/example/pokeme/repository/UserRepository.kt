package com.example.pokeme.repository

import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    val user: FirebaseUser?
    fun register(email: String, password: String, callback: (Result<FirebaseUser>) -> Unit)
    fun login(email: String, password: String, callback: (Result<FirebaseUser>) -> Unit)
    fun logout()
}
