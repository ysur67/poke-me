package com.example.pokeme.repository.implementation

import com.example.pokeme.utils.Result
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage


class MessagesRepository {
    companion object {
        val instance: MessagesRepository = MessagesRepository()
        private const val DEBUG_CODE = "MSG_REPO"
        private const val TOKEN_COLLECTION = "DEVICE_TOKENS"
    }
    private val firestore = Firebase.firestore
    private val firemessage = FirebaseMessaging.getInstance()

    fun registerToken(key: String, value: String) {
        firestore.collection(TOKEN_COLLECTION).document(key).set(hashMapOf("token" to value))
    }

    fun updateToken(key: String) {
        firemessage.token
            .addOnSuccessListener {
                firestore.collection(TOKEN_COLLECTION).document(key).set(hashMapOf("token" to it))
            }
    }

    fun getToken(email: String, callback: (Result<String>) -> Unit) {
        firestore.collection(TOKEN_COLLECTION).document(email).get()
            .addOnSuccessListener {
                val token = it.getString("token") ?: ""
                callback(Result.Success(token))
            }
            .addOnFailureListener {
                callback(Result.Error(it))
            }
    }

    fun sendMessage(title: String, text: String, toToken: String) {
        val builder = RemoteMessage.Builder(toToken)
            .setMessageId(0.toString())
            .addData("title", title)
            .addData("body", text)

        firemessage.send(builder.build())
    }
}
