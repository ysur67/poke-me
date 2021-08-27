package com.example.pokeme.presentation.fragment.auth

import android.widget.EditText
import androidx.fragment.app.Fragment

open class BaseAuthFragment : Fragment() {
    fun displayErrors(errors: HashMap<EditText, String>) {
        for (field in errors.keys) {
            field.error = errors[field]
        }
    }
}
