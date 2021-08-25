package com.example.pokeme.repository

import android.util.Log
import com.example.pokeme.data.models.Message
import kotlinx.coroutines.*


class MessagesRepository : IRepository {
    companion object {
        val instance: MessagesRepository = MessagesRepository()
        private const val DEBUG_CODE = "MSG_REPO"
    }

    private lateinit var connection: String

    override fun init() {
        connection = "ABC"
    }

    fun send(userId: Int, message: Message, callback: OnDataReadyCallback) {
        GlobalScope.launch {
            delay(2000L)
            Log.w(DEBUG_CODE, "Отправлено сообщение пользователю $userId")
//            callback.onDataReady()
        }
    }
}

