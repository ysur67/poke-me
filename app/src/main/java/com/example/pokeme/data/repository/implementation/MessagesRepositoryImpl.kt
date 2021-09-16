package com.example.pokeme.data.repository.implementation

import com.example.pokeme.data.repository.MessageRepository
import com.example.pokeme.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.rxjava3.core.Observable
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

    override fun getToken(email: String) : Observable<Result<String>> {
        return Observable.create{ emitter ->
            firestore.collection(TOKEN_COLLECTION).document(email).get()
                .addOnSuccessListener {
                    val token = it.getString("token") ?: ""
                    emitter.onNext(Result.Success(token))
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    override fun sendMessage(title: String, text: String, toToken: String) {
        val builder = RemoteMessage.Builder(toToken)
            .setMessageId(0.toString())
            .addData("title", title)
            .addData("body", text)

        firemessage.send(builder.build())
    }
}
