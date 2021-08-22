package com.example.pokeme.repository

import android.util.Log
import com.example.pokeme.data.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserRepository : IRepository {
    companion object {
        val instance = UserRepository()
        private const val DEBUG_CODE: String = "USER_REPO"
    }
    private lateinit var connection: String

    override fun init() {

    }

    fun register(email: String, password: String, callback: OnDataReadyCallback) {
        GlobalScope.launch {
            delay(4000L)
            Log.w(DEBUG_CODE, "user is logged in")
            val user = User(email, 1, email)

            callback.onDataReady()
        }
    }

    fun isUserExists(email: String) : Boolean {
        return true
    }

}
