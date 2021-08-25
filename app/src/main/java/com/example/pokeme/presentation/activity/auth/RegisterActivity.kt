package com.example.pokeme.presentation.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.pokeme.databinding.ActivityRegisterBinding
import com.example.pokeme.domain.UserViewModel
import com.example.pokeme.presentation.form.RegisterForm

class RegisterActivity : BaseRegisterActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener{
            val form = RegisterForm(
                binding.editEmail, binding.editPassword, binding.editPasswordRepeat
            )
            val isFormValid = userViewModel.isFormValid(form)
            if (!isFormValid) {
                displayErrors(form.errors)
                return@setOnClickListener
            }
            userViewModel.createUser(binding.editEmail.text.toString(),
                binding.editPassword.text.toString())
        }

        binding.loginHyperlink.setOnClickListener{
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
            }
        }

        userViewModel.isLoading.observe(this, { loading ->
            binding.register.isEnabled = !loading
        })
    }

}
