package com.example.pokeme.data.repository

import com.example.pokeme.data.models.Account
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Observable
import kotlin.collections.HashMap

interface AccountRepository {
    fun getOrCreateAccount(user: FirebaseUser) : Observable<Result<Account>>
    fun updateDocument(id: String, fields: HashMap<String, String>)
    fun getFriends(account: Account) : Observable<Result<List<Account>>>
}
