package com.example.pokeme.data.repository

import com.example.pokeme.data.models.Account
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Observable
import kotlin.collections.HashMap

interface AccountRepository {
    suspend fun getOrCreateAccount(account: Account) : Result<Account>
    fun updateDocument(id: String, fields: HashMap<String, String>)
    suspend fun getFriends(account: Account) : Result<List<Account>>
    fun save(account: Account)
}
