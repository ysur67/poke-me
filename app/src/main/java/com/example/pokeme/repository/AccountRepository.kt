package com.example.pokeme.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException


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
                val email = it.getString("email") ?: throw NullPointerException()
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
            .get()
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    val list = ArrayList<Account>()
                    for (doc in it.result!!) {
                        val friend = Account(doc.data["secondAccount"].toString(), "")
                        list.add(friend)
                    }
                    callback(Result.Success(list))
                }
            }
            .addOnFailureListener {
                callback(Result.Error(it))
            }
    }

}
