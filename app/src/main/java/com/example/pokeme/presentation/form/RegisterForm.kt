package com.example.pokeme.presentation.form

import android.widget.EditText
import com.example.pokeme.utils.Const


class RegisterForm(
    private val email: EditText,
    private val password: EditText,
    private val passwordRepeat: EditText
) : BaseForm() {

    val userEmail: String
        get() = email.text.toString()

    override fun validate() {
        if (email.text.isEmpty()) {
            addError(email, "Почтовый адрес обязателен к заполнению"); return
        }
        if (password.text.isEmpty()) {
            addError(password, "Заполните поле"); return
        }
        if (passwordRepeat.text.isEmpty()) {
            addError(passwordRepeat, "Повторите пароль"); return
        }
        if (password.text.toString() != passwordRepeat.text.toString())
            addError(passwordRepeat, "Пароли не совпадают")
        if (password.text.toString().length <= Const.USER_PASSWORD_LENGTH)
            addError(password, "Длина пароля меньше ${Const.USER_PASSWORD_LENGTH + 1}")
        if (passwordRepeat.text.toString().length <= Const.USER_PASSWORD_LENGTH)
            addError(password, "Длина пароля меньше ${Const.USER_PASSWORD_LENGTH + 1}")
    }
}
