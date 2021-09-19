package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeme.data.models.Account
import com.example.pokeme.utils.Result
import com.example.pokeme.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
    ): BaseViewModel() {
    companion object {
        const val DEBUG_CODE = "USER_VIEW_MODEL"
    }
    private val _currentUser: MutableLiveData<Account> = MutableLiveData(null)

    val user: LiveData<Account>
        get() = _currentUser

    fun createUser(email: String, password: String) {
        loading = true
        authRepo.register(email, password)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError {
                currentException = it as Exception
            }
            .subscribe {
                onUserAuth(it)
            }
    }

    fun loginUser(email: String, password: String) {
        loading = true
        authRepo.login(email, password)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError {
                currentException = it as Exception
            }
            .subscribe {
                onUserAuth(it)
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
}