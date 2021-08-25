package com.example.pokeme.presentation.form

import android.widget.EditText

class LoginForm (
    private val email: EditText,
    private val password: EditText
) : BaseForm() {

    val userEmail: String
    get() = email.text.toString()

    override fun validate() {
        if (email.text.isEmpty()){
            addError(email, "Почтовый адрес обязателен к заполнению"); return
        }
        if (password.text.isEmpty()) {
            addError(password, "Заполните поле"); return
        }
    }
}
