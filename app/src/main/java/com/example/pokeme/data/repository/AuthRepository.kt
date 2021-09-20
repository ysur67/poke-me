package com.example.pokeme.data.repository

import com.example.pokeme.data.models.Account
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Observable

interface AuthRepository {
    val currentAccount: Account?
    suspend fun register(email: String, password: String) : Result<Account>
    suspend fun login(email: String, password: String) : Result<Account>
    fun logout()
}
