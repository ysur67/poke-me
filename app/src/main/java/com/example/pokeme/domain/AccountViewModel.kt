package com.example.pokeme.domain


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.Account
import com.example.pokeme.repository.AccountRepository
import com.google.firebase.auth.FirebaseUser

class AccountViewModel : ViewModel() {
    private val accountRepo = AccountRepository.instance
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _currentAccount: MutableLiveData<Account> = MutableLiveData(null)

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val account: LiveData<Account>
        get() = _currentAccount

    fun updateAccountByUser(user: FirebaseUser) {
        _isLoading.postValue(true)
        _currentAccount.postValue(accountRepo.getOrCreateAccount(user))
        _isLoading.postValue(false)
    }

    fun updateAccount(account: Account) {
        _isLoading.postValue(true)
        accountRepo.updateRow(account.email, account.toHashMap())
        _currentAccount.postValue(account)
        _isLoading.postValue(false)
    }
}
