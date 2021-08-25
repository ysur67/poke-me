package com.example.pokeme.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.User
import com.example.pokeme.presentation.form.LoginForm
import com.example.pokeme.presentation.form.RegisterForm
import com.example.pokeme.repository.OnDataReadyCallback
import com.example.pokeme.repository.Result
import com.example.pokeme.repository.UserRepository
import java.lang.Exception
import java.nio.file.attribute.UserPrincipal

class UserViewModel: ViewModel() {
    companion object {
        const val DEBUG_CODE = "USER_VIEW_MODEL"
    }

    private val userRepo: UserRepository = UserRepository.instance
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private lateinit var _currentUser: MutableLiveData<User>

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val userAuthCallback = object : OnDataReadyCallback {
        override fun onDataReady(result: Result<User>) {
            when (result) {
                is Result.Success -> {
                    val user = result.data as User
                    _currentUser = MutableLiveData(user)
                }
                is Result.Error -> {
                    val exception = result.ex as Exception
                    throw exception
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun createUser(email: String, password: String) {
        _isLoading.postValue(true)
        userRepo.register(email, password, userAuthCallback)
    }

    fun loginUser(email: String, password: String) {
        _isLoading.postValue(true)
        userRepo.login(email, password, userAuthCallback)
    }

    fun isFormValid(form: RegisterForm) : Boolean {
        form.validate()
        return form.isValid && !isUserExists(form.userEmail)
    }

    fun isFormValid(form: LoginForm) : Boolean {
        form.validate()
        return form.isValid && !isUserExists(form.userEmail)
    }

    private fun isUserExists(email: String) : Boolean {
        return false
    }
}