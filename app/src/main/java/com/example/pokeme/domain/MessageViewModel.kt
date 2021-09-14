package com.example.pokeme.domain

import com.example.pokeme.repository.implementation.MessagesRepository
import com.example.pokeme.utils.Result


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
