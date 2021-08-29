package com.example.pokeme.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.pokeme.R
import com.example.pokeme.databinding.ActivityAuthBinding
import com.example.pokeme.databinding.ActivityMainBinding
import com.example.pokeme.domain.UserViewModel
import com.example.pokeme.presentation.fragment.auth.LoginFragment
import com.example.pokeme.presentation.fragment.auth.RegisterFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private val userViewModel: UserViewModel by viewModels()

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            when (tab?.contentDescription) {
                resources.getString(R.string.loginTabItem) -> {
                    val loginFragment = LoginFragment.newInstance()
                    changeFragment(loginFragment)
                }
                resources.getString(R.string.registerTabItem) -> {
                    val registerFragment = RegisterFragment.newInstance()
                    changeFragment(registerFragment)
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.tabLayout.addOnTabSelectedListener(tabSelectedListener)
        userViewModel.isLoading.observe(this, {
            binding.tabLayout.isEnabled = !it
        })
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

    private fun changeFragment(new: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.authFrameLayout.id, new)
            commit()
        }
    }
}
