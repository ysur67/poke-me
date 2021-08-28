package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.User
import com.example.pokeme.presentation.form.LoginForm
import com.example.pokeme.presentation.form.RegisterForm
import com.example.pokeme.repository.OnDataReadyCallback
import com.example.pokeme.repository.Result
import com.example.pokeme.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class UserViewModel: ViewModel() {
    companion object {
        const val DEBUG_CODE = "USER_VIEW_MODEL"
    }

    private val userRepo: UserRepository = UserRepository.instance
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _currentUser: MutableLiveData<FirebaseUser> = MutableLiveData(userRepo.user)
    private val _currentException: MutableLiveData<Exception> = MutableLiveData(null)

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val exception: LiveData<Exception>
        get() = _currentException

    val user: LiveData<FirebaseUser>
        get() = _currentUser

    private val userAuthCallback = object : OnDataReadyCallback {
        override fun onDataReady(result: Result<FirebaseUser>) {
            when (result) {
                is Result.Success -> {
                    val user = result.data as FirebaseUser
                    _currentUser.postValue(user)
                }
                is Result.Error -> {
                    val exception = result.ex as Exception
                    _currentException.postValue(exception)
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

    fun logout() {
        _isLoading.postValue(true)
        userRepo.logout()
        _isLoading.postValue(false)
        _currentUser.postValue(null)
    }

    fun isFormValid(form: RegisterForm) : Boolean {
        form.validate()
        return form.isValid
    }

    fun isFormValid(form: LoginForm) : Boolean {
        form.validate()
        return form.isValid
    }
}