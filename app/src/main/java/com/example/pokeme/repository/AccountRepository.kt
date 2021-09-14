package com.example.pokeme.repository

import androidx.compose.animation.core.snap
import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton


interface AccountRepository {
    fun getOrCreateAccount(user: FirebaseUser, callback: (Result<Account>) -> Unit)
    fun updateDocument(id: String, fields: HashMap<String, String>)
    fun getFriends(account: Account, callback: (Result<List<Account>>) -> Unit)
}

class AccountRepositoryFirebase @Inject constructor(
    private val connection: FirebaseFirestore
    ) : AccountRepository {

    override fun getOrCreateAccount(user: FirebaseUser, callback: (Result<Account>) -> Unit) {
        val email = user.email!!
        connection.collection(ACCOUNT_COLLECTION).document(email).get()
            .addOnSuccessListener {
                val username = it.getString("username") ?: ""
                callback(Result.Success(Account(email, username)))
            }
            .addOnFailureListener { callback(Result.Error(it)) }
    }

    override fun updateDocument(id: String, fields: HashMap<String, String>) {
        connection.collection(ACCOUNT_COLLECTION).document(id).set(fields)
    }

    override fun getFriends(account: Account, callback: (Result<List<Account>>) -> Unit) {
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

    companion object {
        private const val ACCOUNT_COLLECTION: String = "accounts"
        private const val FRIENDS_COLLECTION: String = "friends"
    }
}
