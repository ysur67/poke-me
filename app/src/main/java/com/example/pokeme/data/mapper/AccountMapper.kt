package com.example.pokeme.data.mapper

import com.example.pokeme.data.models.Account
import com.example.pokeme.utils.firebase.getStringOrEmpty
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import java.lang.NullPointerException


fun Account.Companion.toAccount(user: FirebaseUser) : Account {
    val email = user.email!!
    return Account(user.uid, email, Account.getDefaultUsername(email))
}

fun Account.Companion.toAccount(document: DocumentSnapshot) : Account {
    val username = document.getStringOrEmpty("username")
    val email = document.getString("email")
        ?: throw NullPointerException("There is no email for this user, check firestore")
    return Account(document.id, email, username)
}
