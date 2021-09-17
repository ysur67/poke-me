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
    val username: String,
) {
    companion object {
        fun getDefaultUsername(email: String) : String {
            return email.split("@")[0]
        }

        fun generateRandomId() : String {
            return UUID.randomUUID().toString()
        }
    }

    fun toHashMap() : HashMap<String, String> {
        return hashMapOf(
            "email" to email,
            "username" to username
        )
    }
}
