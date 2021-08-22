package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.User
import com.example.pokeme.repository.OnDataReadyCallback
import com.example.pokeme.repository.UserRepository

class UserViewModel: ViewModel() {
    private val userRepo: UserRepository = UserRepository.instance
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private lateinit var _currentUser: MutableLiveData<User>

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun createUser(email: String, password: String) {
        _isLoading.postValue(true)
        val callback = object : OnDataReadyCallback {
            override fun onDataReady() {
                _isLoading.postValue(false)

//                _currentUser.postValue()
            }

        }
        userRepo.register(email, password, callback)
    }
}