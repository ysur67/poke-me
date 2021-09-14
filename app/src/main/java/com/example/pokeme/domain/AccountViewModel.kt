package com.example.pokeme.domain


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeme.data.models.Account
import com.example.pokeme.repository.AccountRepository
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    val accountRepo: AccountRepository
    ) : BaseViewModel() {

    private var _currentAccount: MutableLiveData<Account> = MutableLiveData(null)
    private val _friends: MutableLiveData<ArrayList<Account>> = MutableLiveData(null)

    val account: LiveData<Account>
        get() = _currentAccount
    val friends: LiveData<ArrayList<Account>>
        get() = _friends

    fun updateAccountByUser(user: FirebaseUser) {
        loading = true
        accountRepo.getOrCreateAccount(user) {
            when(it) {
                is Result.Success -> { _currentAccount.postValue(it.data) }
                is Result.Error -> { currentException = it.ex }
            }
        }
        loading = false
    }

    fun updateAccount(account: Account) {
        loading = true
        accountRepo.updateDocument(account.email, account.toHashMap())
        _currentAccount.postValue(account)
        loading = false
    }

    fun updateFriends() {
        val currentAccount = _currentAccount.value ?: return
        accountRepo.getFriends(currentAccount) {
            when (it) {
                is Result.Success -> { _friends.postValue(it.data as ArrayList<Account>) }
                is Result.Error -> { currentException = it.ex }
            }
        }
    }

}
