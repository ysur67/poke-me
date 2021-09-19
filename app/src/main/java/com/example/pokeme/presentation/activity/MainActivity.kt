package com.example.pokeme.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pokeme.App
import com.example.pokeme.R
import com.example.pokeme.databinding.ActivityMainBinding
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.domain.AccountViewModel
import com.example.pokeme.domain.AuthViewModel
import com.example.pokeme.domain.MessageViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val authViewModel: AuthViewModel by viewModels { viewModelFactory }
    private val accountViewModel: AccountViewModel by viewModels { viewModelFactory }
    private val messageViewModel: MessageViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigation.setupWithNavController(navController)

        authViewModel.user.observe(this, {
            if (it == null) {
                Intent(this, AuthActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
        })
        val currentUser = authViewModel.user.value ?: return
        accountViewModel.getAccountFromRemote(currentUser)
        messageViewModel.updateToken(currentUser.email)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutOption -> {
                authViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
