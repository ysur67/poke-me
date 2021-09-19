package com.example.pokeme.data.repository

import com.example.pokeme.data.models.Account
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Observable

interface UserRepository {
    val currentAccount: Account?
    fun register(email: String, password: String) : Observable<Result<Account>>
    fun login(email: String, password: String) : Observable<Result<Account>>
    fun logout()
}
