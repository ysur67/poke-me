package com.example.pokeme.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.User
import com.example.pokeme.data.models.Message
import com.example.pokeme.repository.MessagesRepository
import com.example.pokeme.repository.OnDataReadyCallback
import com.example.pokeme.repository.Result


class MessageViewModel : ViewModel() {
    private val messageRepo: MessagesRepository = MessagesRepository.instance
    private val _isSending: MutableLiveData<Boolean> = MutableLiveData(false)

    val isSending: LiveData<Boolean>
        get() = _isSending

    fun sendMessage(email: String, title: String, body: String) {
        _isSending.postValue(true)
        messageRepo.getToken(email) {
            if (it is Result.Success) {
                messageRepo.sendMessage(title, body, it.data)
            }
        }
        _isSending.postValue(false)
    }

}
