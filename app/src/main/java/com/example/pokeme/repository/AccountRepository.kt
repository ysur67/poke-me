package com.example.pokeme.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Observable


class AccountRepository {
    companion object {
        val instance = AccountRepository()
        private const val ACCOUNT_COLLECTION: String = "accounts"
    }

    private val connection = Firebase.firestore

    fun getOrCreateAccount(user: FirebaseUser) : LiveData<Account>  {
        val email = user.email!!
        val result = MutableLiveData<Account>(null)
        connection.collection(ACCOUNT_COLLECTION).document(email).get()
            .addOnFailureListener {
                result.postValue(null)
            }
            .addOnSuccessListener {
                val account = Account(email, it.getString("username") ?: "")
                result.postValue(account)
            }
        return result
    }

    fun updateRow(id: String, fields: HashMap<String, String>) {
        connection.collection(ACCOUNT_COLLECTION).document(id).set(fields)
    }

}
