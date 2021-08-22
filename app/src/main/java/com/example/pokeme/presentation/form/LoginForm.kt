package com.example.pokeme.presentation.form

import android.widget.EditText
import com.example.pokeme.repository.UserRepository

class LoginForm (
    private val email: EditText,
    private val password: EditText
) : BaseForm() {
    private val userRepo = UserRepository.instance

    override fun validate() {
        if (email.text.isEmpty()){
            addError(email, "Почтовый адрес обязателен к заполнению"); return
        }
        if (password.text.isEmpty()) {
            addError(password, "Заполните поле"); return
        }

        if (!userRepo.isUserExists(email.text.toString()))
            addError(email, "Аккаунта с таким почтовым адресом не существует")
    }
}
