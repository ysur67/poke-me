package com.example.pokeme.domain

import com.example.pokeme.data.repository.MessageRepository
import com.example.pokeme.utils.Result
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class MessageViewModel @Inject constructor(
    private val messageRepo: MessageRepository
    ): BaseViewModel() {

    fun sendMessage(email: String, title: String, body: String) {
        loading = true
        messageRepo.getToken(email)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError {
                currentException = it as Exception
            }
            .subscribe {
                if (it is Result.Success) {
                    messageRepo.sendMessage(title, body, it.data)
                }
            }
        loading = false
    }

    fun updateToken(email: String) {
        messageRepo.updateToken(email)
    }
}
