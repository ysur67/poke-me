package com.example.pokeme.presentation.activity.auth

import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

open class BaseRegisterActivity : AppCompatActivity() {
    fun displayErrors(errors: HashMap<EditText, String>) {
        for (field in errors.keys) {
            field.error = errors[field]
        }
    }
}
