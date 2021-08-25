package com.example.pokeme.presentation.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.pokeme.databinding.ActivityLoginBinding
import com.example.pokeme.domain.UserViewModel
import com.example.pokeme.presentation.form.LoginForm
import com.example.pokeme.utils.activity.makeToast
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginActivity : BaseRegisterActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            val form = LoginForm(binding.editEmail, binding.editPassword)
            val isFormValid = userViewModel.isFormValid(form)
            if (!isFormValid) {
                displayErrors(form.errors)
                return@setOnClickListener
            }
            userViewModel.loginUser(
                binding.editEmail.text.toString(),
                binding.editPassword.text.toString()
            )
        }
        binding.registerHyperLink.setOnClickListener{
            Intent(this, RegisterActivity::class.java).apply{
                startActivity(this)
            }
        }

        userViewModel.exception.observe(this, {
            when (it) {
                is FirebaseAuthInvalidCredentialsException -> {
                    makeToast("Проверьте введенные данные")
                }
            }
        })
    }
}