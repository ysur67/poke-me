package com.example.pokeme.repository

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Error(val ex: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$ex]"
        }
    }
}
