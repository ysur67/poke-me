package com.example.pokeme.data.mapper

import com.example.pokeme.data.models.Account
import com.google.firebase.auth.FirebaseUser


fun Account.Companion.toAccount(user: FirebaseUser) : Account {
    val email = user.email!!
    return Account(user.uid, email, Account.getDefaultUsername(email))
}
