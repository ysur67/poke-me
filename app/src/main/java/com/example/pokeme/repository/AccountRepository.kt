package com.example.pokeme.repository

import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseUser

interface AccountRepository {
    fun getOrCreateAccount(user: FirebaseUser, callback: (Result<Account>) -> Unit)
    fun updateDocument(id: String, fields: HashMap<String, String>)
    fun getFriends(account: Account, callback: (Result<List<Account>>) -> Unit)
}
