package com.example.pokeme.repository

import androidx.compose.animation.core.snap
import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AccountRepository {
    companion object {
        val instance = AccountRepository()
        private const val ACCOUNT_COLLECTION: String = "accounts"
        private const val FRIENDS_COLLECTION: String = "friends"
    }

    private val connection = Firebase.firestore

    fun getOrCreateAccount(user: FirebaseUser, callback: (Result<Account>) -> Unit) {
        val email = user.email!!
        connection.collection(ACCOUNT_COLLECTION).document(email).get()
            .addOnSuccessListener {
                val username = it.getString("username") ?: ""
                callback(Result.Success(Account(email, username)))
            }
            .addOnFailureListener { callback(Result.Error(it)) }
    }

    fun updateRow(id: String, fields: HashMap<String, String>) {
        connection.collection(ACCOUNT_COLLECTION).document(id).set(fields)
    }

    fun getFriends(account: Account, callback: (Result<List<Account>>) -> Unit) {
        connection.collection(FRIENDS_COLLECTION)
            .whereEqualTo("firstAccount", account.email)
            .addSnapshotListener{snapshot, error ->
                if (error != null) {
                    callback(Result.Error(error))
                    return@addSnapshotListener
                }
                val friends = ArrayList<Account>()
                if (snapshot != null && !snapshot.isEmpty) {
                    for (doc in snapshot) {
                        val email = doc.getString("secondAccount") ?: "your friend's email"
                        friends.add(Account(email, ""))
                    }
                }
                callback(Result.Success(friends))
            }
    }
}
