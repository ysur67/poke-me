package com.example.pokeme.utils.fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.makeToast(text: String) {
    Toast.makeText(activity, text, Toast.LENGTH_LONG).show()
}

fun Fragment.makeSnack(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
}
