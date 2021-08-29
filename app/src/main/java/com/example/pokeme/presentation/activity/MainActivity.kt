package com.example.pokeme.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pokeme.databinding.ActivityMainBinding
import com.example.pokeme.domain.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.floatingActionButton2.setOnClickListener{
            userViewModel.logout()
        }
        userViewModel.user.observe(this, {
            if (it == null) {
                Intent(this, AuthActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
        })
    }
}