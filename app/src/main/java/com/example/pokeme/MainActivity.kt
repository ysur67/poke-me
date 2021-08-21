package com.example.pokeme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.pokeme.data.models.Account
import com.example.pokeme.data.models.Message
import com.example.pokeme.databinding.ActivityMainBinding
import com.example.pokeme.domain.MessageViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val messageViewModel: MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        messageViewModel.isSending.observe(this, { value: Boolean ->
            if (value) {
                runOnUiThread{ binding.sendMessageButton.isEnabled = false }
            } else {
                runOnUiThread{ binding.sendMessageButton.isEnabled = true }
            }
        })

        binding.sendMessageButton.setOnClickListener{
            val account = Account("Vasya", 1)
            val messageText = binding.editMessageText.text.toString()
            val message = Message(messageText, account)
            messageViewModel.sendMessage(message, account)
        }
    }
}
