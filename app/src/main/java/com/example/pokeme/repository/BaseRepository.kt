package com.example.pokeme.repository

import com.google.firebase.auth.FirebaseUser

interface OnDataReadyCallback {
    fun onDataReady(result: Result<FirebaseUser>)
}
