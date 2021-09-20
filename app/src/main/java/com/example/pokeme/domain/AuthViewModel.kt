package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokeme.data.models.Account
import com.example.pokeme.utils.Result
import com.example.pokeme.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
    ): BaseViewModel() {

    private val _currentUser: MutableLiveData<Account> = MutableLiveData(authRepo.currentAccount)
    val user: LiveData<Account>
        get() = _currentUser

    fun createUser(email: String, password: String) {
        loading = true
        viewModelScope.launch {
            onUserAuth(authRepo.register(email, password))
        }
    }

    fun loginUser(email: String, password: String) {
        loading = true
        viewModelScope.launch {
            onUserAuth(authRepo.login(email, password))
        }
    }

    fun logout() {
        loading = true
        authRepo.logout()
        loading = false
        _currentUser.postValue(null)
    }

    private fun onUserAuth(result: Result<Account>) {
        when (result) {
            is Result.Success -> {
                _currentUser.postValue(result.data)
            }
            is Result.Error -> { currentException = result.ex }
        }
        loading = false
    }

    companion object {
        const val DEBUG_CODE = "USER_VIEW_MODEL"
    }
}