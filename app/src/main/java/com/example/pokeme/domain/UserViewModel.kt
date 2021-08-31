package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeme.presentation.form.LoginForm
import com.example.pokeme.presentation.form.RegisterForm
import com.example.pokeme.repository.OnDataReadyCallback
import com.example.pokeme.repository.Result
import com.example.pokeme.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class UserViewModel: BaseViewModel() {
    companion object {
        const val DEBUG_CODE = "USER_VIEW_MODEL"
    }

    private val userRepo: UserRepository = UserRepository.instance
    private val _currentUser: MutableLiveData<FirebaseUser> = MutableLiveData(userRepo.user)

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
                    currentException = result.ex
                }
            }
            loading = false
        }
    }

    fun createUser(email: String, password: String) {
        loading = true
        userRepo.register(email, password, userAuthCallback)
    }

    fun loginUser(email: String, password: String) {
        loading = true
        userRepo.login(email, password, userAuthCallback)
    }

    fun logout() {
        loading = true
        userRepo.logout()
        loading = false
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