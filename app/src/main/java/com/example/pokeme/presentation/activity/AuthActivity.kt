package com.example.pokeme.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokeme.R
import com.example.pokeme.databinding.ActivityAuthBinding
import com.example.pokeme.domain.UserViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)

        userViewModel.user.observe(this, {
            if (it != null) {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
        })
        userViewModel.exception.observe(this, {
            when (it) {
                is FirebaseAuthInvalidCredentialsException -> {

                }
            }
        })
    }
}
