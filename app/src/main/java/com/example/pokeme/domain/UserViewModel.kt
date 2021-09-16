package com.example.pokeme.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeme.presentation.form.LoginForm
import com.example.pokeme.presentation.form.RegisterForm
import com.example.pokeme.utils.Result
import com.example.pokeme.data.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepo: UserRepository
    ): BaseViewModel() {
    companion object {
        const val DEBUG_CODE = "USER_VIEW_MODEL"
    }
    private val _currentUser: MutableLiveData<FirebaseUser> = MutableLiveData(null)

    val user: LiveData<FirebaseUser>
        get() = _currentUser

    fun createUser(email: String, password: String) {
        loading = true
        userRepo.register(email, password)
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
        userRepo.login(email, password)
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

    private fun onUserAuth(result: Result<FirebaseUser>) {
        when (result) {
            is Result.Success -> {
                _currentUser.postValue(result.data)
            }
            is Result.Error -> { currentException = result.ex }
        }
        loading = false
    }
}