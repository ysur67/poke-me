package com.example.pokeme.data.models

data class Account(
    val email: String,
    val username: String,
) {
    companion object {
        fun getDefaultUsername(email: String) : String {
            return email.split("@")[0]
        }
    }

    fun toHashMap() : HashMap<String, String> {
        return hashMapOf(
            "email" to email,
            "username" to username
        )
    }
}
