package com.example.pokeme.data.repository.implementation

import com.example.pokeme.data.repository.MessageRepository
import com.example.pokeme.utils.Result
import com.example.pokeme.utils.firebase.getStringOrEmpty
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class MessagesRepositoryImpl @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val firemessage: FirebaseMessaging
    ) : MessageRepository {
    companion object {
        private const val DEBUG_CODE = "MSG_REPO"
        private const val TOKEN_COLLECTION = "DEVICE_TOKENS"
    }

    override fun registerToken(key: String, value: String) {
        firestore.collection(TOKEN_COLLECTION).document(key).set(hashMapOf("token" to value))
    }

    override fun updateToken(key: String) {
        firemessage.token
            .addOnSuccessListener {
                firestore.collection(TOKEN_COLLECTION).document(key).set(hashMapOf("token" to it))
            }
    }

    override suspend fun getToken(email: String) : Result<String> {
        lateinit var result: Result<String>
        firestore.collection(TOKEN_COLLECTION).document(email).get()
            .addOnSuccessListener {
                result = Result.Success(it.getStringOrEmpty("token"))
            }
            .addOnFailureListener {
                result = Result.Error(it)
            }
            .await()
        return result
    }

    override fun sendMessage(title: String, text: String, toToken: String) {
        val builder = RemoteMessage.Builder(toToken)
            .setMessageId(0.toString())
            .addData("title", title)
            .addData("body", text)

        firemessage.send(builder.build())
    }
}
