package com.example.pokeme.repository

import com.example.pokeme.data.models.User


interface IRepository {
    fun init()
}

interface IRequestResult {
    val resultCode: Int
}

interface OnDataReadyCallback {
    fun onDataReady(result: Result<User>)

}
