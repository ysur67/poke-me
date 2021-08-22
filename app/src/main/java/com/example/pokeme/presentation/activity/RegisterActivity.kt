package com.example.pokeme.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import com.example.pokeme.data.models.User
import com.example.pokeme.databinding.ActivityRegisterBinding
import com.example.pokeme.domain.UserViewModel
import com.example.pokeme.presentation.form.RegisterForm
import com.example.pokeme.utils.activity.makeToast

class RegisterActivity : AppCompatActivity() {
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
            form.validate()
            if (!form.isValid) {
                runOnUiThread{ displayErrors(form.errors) }
                return@setOnClickListener
            }
            userViewModel.createUser(binding.editEmail.text.toString(),
                binding.editPassword.text.toString())
        }
    }

    private fun displayErrors(errors: HashMap<EditText, String>) {
        for (field in errors.keys) {
            field.error = errors[field]
        }
    }
}
