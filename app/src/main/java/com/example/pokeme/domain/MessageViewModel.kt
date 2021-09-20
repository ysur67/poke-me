package com.example.pokeme.domain

import androidx.lifecycle.viewModelScope
import com.example.pokeme.data.repository.MessageRepository
import com.example.pokeme.utils.Result
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject


class MessageViewModel @Inject constructor(
    private val messageRepo: MessageRepository
    ): BaseViewModel() {

    fun sendMessage(email: String, title: String, body: String) {
        loading = true
        viewModelScope.launch {
            when (val result = messageRepo.getToken(email)) {
                is Result.Success -> messageRepo.sendMessage(title, body, result.data)
                is Result.Error -> throw NullPointerException("There is no token with email $email")
            }
            loading = false
        }
    }

    fun updateToken(email: String) {
        messageRepo.updateToken(email)
    }
}
