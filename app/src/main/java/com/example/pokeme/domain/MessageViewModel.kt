package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.Account
import com.example.pokeme.data.models.Message
import com.example.pokeme.repository.MessagesRepository
import com.example.pokeme.repository.OnDataReadyCallback


class MessageViewModel : ViewModel() {
    private val messageRepo: MessagesRepository = MessagesRepository.instance
    private val _isSending: MutableLiveData<Boolean> = MutableLiveData(false)

    val isSending: LiveData<Boolean>
        get() = _isSending

    fun sendMessage(content: Message, user: Account) {
        _isSending.postValue(true)
        val callback = object : OnDataReadyCallback{
            override fun onDataReady() {
                _isSending.postValue(false)
            }
        }
        messageRepo.send(user.id, content, callback)
    }
}
