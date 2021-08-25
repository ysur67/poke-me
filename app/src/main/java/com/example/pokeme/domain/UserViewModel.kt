package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.User
import com.example.pokeme.presentation.form.BaseForm
import com.example.pokeme.presentation.form.LoginForm
import com.example.pokeme.presentation.form.RegisterForm
import com.example.pokeme.repository.OnDataReadyCallback
import com.example.pokeme.repository.UserRepository
import java.text.Normalizer

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

    fun isFormValid(form: RegisterForm) : Boolean {
        form.validate()
        return form.isValid && !isUserExists(form.userEmail)
    }

    fun isFormValid(form: LoginForm) : Boolean {
        form.validate()
        return form.isValid && isUserExists(form.userEmail)
    }

    private fun isUserExists(email: String) : Boolean {
        _isLoading.postValue(true)
        var result = false
        result = true
        _isLoading.postValue(false)
        return result
    }
}