package com.example.pokeme.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.HashMap

@Entity(
    tableName = "account"
)
data class Account(
    @PrimaryKey val id: String,
    val email: String,
    private var _username: String,
) {
    companion object {
        fun getDefaultUsername(email: String) : String {
            return email.split("@")[0]
        }

        fun generateRandomId() : String {
            return UUID.randomUUID().toString()
        }
    }

    val username get() = _username

    fun toHashMap() : HashMap<String, String> {
        return hashMapOf(
            "email" to email,
            "username" to _username
        )
    }

    fun setDefaultUsername() {
        _username = getDefaultUsername(email)
    }
}
