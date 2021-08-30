package com.example.pokeme.domain


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokeme.data.models.Account
import com.example.pokeme.repository.AccountRepository
import com.example.pokeme.repository.Result
import com.google.firebase.auth.FirebaseUser

class AccountViewModel : BaseViewModel() {
    private val accountRepo = AccountRepository.instance
    private var _currentAccount: MutableLiveData<Account> = MutableLiveData(null)
    private val _friends: MutableLiveData<ArrayList<Account>> = MutableLiveData(null)

    val account: LiveData<Account>
        get() = _currentAccount
    val friends: LiveData<ArrayList<Account>>
        get() = _friends

    fun updateAccountByUser(user: FirebaseUser) {
        loading = true
        _currentAccount = accountRepo.getOrCreateAccount(user) as MutableLiveData<Account>
        loading = false
    }

    fun updateAccount(account: Account) {
        loading = true
        accountRepo.updateRow(account.email, account.toHashMap())
        _currentAccount.postValue(account)
        loading = false
    }

    fun updateFriends() {
        val currentAccount = _currentAccount.value ?: return
        accountRepo.getFriends(currentAccount) {
            when (it) {
                is Result.Success -> {
                    _friends.postValue(it.data as ArrayList<Account>)
                }
                is Result.Error -> {
                    currentException = it.ex
                }
            }
        }
    }

}
