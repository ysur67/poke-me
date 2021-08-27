package com.example.pokeme.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pokeme.databinding.ActivityMainBinding
import com.example.pokeme.domain.MessageViewModel
import com.example.pokeme.presentation.fragment.auth.LoginFragment
import com.example.pokeme.presentation.fragment.auth.RegisterFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.checkoutLogin.setOnClickListener{
            val loginFragment = LoginFragment.newInstance()
            changeFragment(loginFragment)
        }
        binding.checkoutRegister.setOnClickListener{
            val registerFragment = RegisterFragment.newInstance()
            changeFragment(registerFragment)
        }
    }

    private fun changeFragment(new: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.authFrameLayout.id, new)
            commit()
        }
    }
}
