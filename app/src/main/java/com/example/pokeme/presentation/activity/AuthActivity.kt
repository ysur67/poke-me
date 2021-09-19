package com.example.pokeme.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokeme.App
import com.example.pokeme.R
import com.example.pokeme.databinding.ActivityAuthBinding
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.domain.AuthViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: AuthViewModel by viewModels{ viewModelFactory }

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController)

        viewModel.user.observe(this, {
            if (it != null) {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
        })
        viewModel.exception.observe(this, {
            when (it) {
                is FirebaseAuthInvalidCredentialsException -> {

                }
            }
        })
    }
}
