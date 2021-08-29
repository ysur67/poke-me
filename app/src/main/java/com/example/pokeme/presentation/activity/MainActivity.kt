package com.example.pokeme.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokeme.R
import com.example.pokeme.databinding.ActivityMainBinding
import com.example.pokeme.domain.AccountViewModel
import com.example.pokeme.domain.UserViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigation.setupWithNavController(navController)

        userViewModel.user.observe(this, {
            if (it == null) {
                Intent(this, AuthActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
        })
        val currentUser = userViewModel.user.value ?: return
        accountViewModel.updateAccountByUser(currentUser)
    }
}
