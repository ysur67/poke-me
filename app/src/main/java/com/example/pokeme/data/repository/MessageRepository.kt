package com.example.pokeme.data.repository

import io.reactivex.rxjava3.core.Observable
import com.example.pokeme.utils.Result

interface MessageRepository {

    fun registerToken(key: String, value: String)
    fun updateToken(key: String)
    fun getToken(email: String) : Observable<Result<String>>
    fun sendMessage(title: String, text: String, toToken: String)
}