package com.example.pokeme.repository


interface IRepository {
    fun init()
}

interface IRequestResult {
    val resultCode: Int
}

interface OnDataReadyCallback {
    fun onDataReady()

}
