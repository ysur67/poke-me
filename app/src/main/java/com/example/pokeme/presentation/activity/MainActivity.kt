package com.example.pokeme.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.pokeme.R
import com.example.pokeme.databinding.ActivityMainBinding
import com.example.pokeme.domain.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.friends_menu_item -> {

                }
                R.id.pokes_menu_item -> {

                }
                R.id.profile_menu_item -> {

                }
            }
            true
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

    private fun changeFragment(new: Fragment) {

    }
}