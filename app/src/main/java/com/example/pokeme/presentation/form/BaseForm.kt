package com.example.pokeme.presentation.form

import android.widget.EditText


interface IForm {
    fun validate()
    val isValid: Boolean
    val errors: HashMap<EditText, String>
}

abstract class BaseForm : IForm {
    private var _errors: HashMap<EditText, String> = HashMap()

    override val isValid: Boolean
        get() = errors.keys.size == 0
    override val errors: HashMap<EditText, String>
        get() = _errors

    protected fun addError(field: EditText, message: String) {
        _errors[field] = message
    }
}
