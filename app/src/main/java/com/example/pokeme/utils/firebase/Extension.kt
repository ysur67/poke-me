package com.example.pokeme.utils.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

val QuerySnapshot?.isNotEmpty: Boolean
    get() = this != null && !this.isEmpty

fun QueryDocumentSnapshot.getStringOrEmpty(key: String) : String {
    return getString(key) ?: ""
}

fun DocumentSnapshot.getStringOrEmpty(key: String) : String {
    return getString(key) ?: ""
}
