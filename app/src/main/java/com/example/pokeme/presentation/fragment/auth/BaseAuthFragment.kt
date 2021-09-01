package com.example.pokeme.presentation.fragment.auth

import android.widget.EditText
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import com.example.pokeme.R
import com.example.pokeme.utils.fragment.makeSnack
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

abstract class BaseAuthFragment : Fragment() {
    fun displayErrors(errors: HashMap<EditText, String>) {
        for (field in errors.keys) {
            field.error = errors[field]
        }
    }

    protected abstract fun onLoading(value: Boolean)
    fun onError(error: Exception?) {
        if (error == null) return
        when(error) {
            is FirebaseAuthInvalidCredentialsException -> {
                makeSnack(getString(R.string.auth_invalid))
            }
            is FirebaseAuthUserCollisionException -> {
                makeSnack(getString(R.string.auth_invalid))
            }
            is FirebaseAuthInvalidUserException -> {
                makeSnack(getString(R.string.user_doesnot_exist))
            }
        }
    }
}
