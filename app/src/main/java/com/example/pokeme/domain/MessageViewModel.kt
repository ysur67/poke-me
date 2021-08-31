package com.example.pokeme.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.User
import com.example.pokeme.data.models.Message
import com.example.pokeme.repository.MessagesRepository
import com.example.pokeme.repository.Result


class MessageViewModel : BaseViewModel() {
    private val messageRepo: MessagesRepository = MessagesRepository.instance

    fun sendMessage(email: String, title: String, body: String) {
        loading = true
        messageRepo.getToken(email) {
            if (it is Result.Success) {
                messageRepo.sendMessage(title, body, it.data)
            }
        }
        loading = false
    }

}
