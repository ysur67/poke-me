package com.example.pokeme.repository

import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AccountRepository {
    companion object {
        val instance = AccountRepository()
        private const val ACCOUNT_COLLECTION: String = "accounts"
    }

    private val connection = Firebase.firestore

    fun getOrCreateAccount(user: FirebaseUser) : Account {
        val email = user.email!!
        val account = Account(email, Account.getDefaultUsername(email))
        connection.collection(ACCOUNT_COLLECTION).document(account.email)
            .set(account.toHashMap())
        return account
    }

    fun updateRow(id: String, fields: HashMap<String, String>) {
        connection.collection(ACCOUNT_COLLECTION).document(id).set(fields)
    }

}
