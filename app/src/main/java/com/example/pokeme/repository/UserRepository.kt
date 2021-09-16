package com.example.pokeme.repository

import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Observable

interface UserRepository {
    val user: FirebaseUser?
    fun register(email: String, password: String) : Observable<Result<FirebaseUser>>
    fun login(email: String, password: String) : Observable<Result<FirebaseUser>>
    fun logout()
}
