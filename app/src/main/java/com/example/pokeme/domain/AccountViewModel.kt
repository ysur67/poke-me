package com.example.pokeme.domain


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokeme.data.models.Account
import com.example.pokeme.data.repository.AccountRepository
import com.example.pokeme.di.module.DataSourceModule_ProvideFirebaseAuthFactory
import com.example.pokeme.utils.Result
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
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

    fun updateAccount(account: Account) {
        accountRepo.updateDocument(account.email, account.toHashMap())
        _currentAccount.postValue(account)
    }

    fun getAccountFromRemote(account: Account) {
        loading = true
        viewModelScope.launch {
            when (val result = accountRepo.getOrCreateAccount(account)) {
                is Result.Success -> onAccountRetrieved(result.data)
                is Result.Error -> currentException = result.ex
            }
        }
    }

    fun updateFriends() {
        val currentAccount = _currentAccount.value ?: return
        viewModelScope.launch {
            when (val result = accountRepo.getFriends(currentAccount)) {
                is Result.Success -> _friends.postValue(result.data as ArrayList<Account>)
            }
        }
    }

    private fun onAccountRetrieved(account: Account) {
        var newAccount = account
        // If user was logged in currently and didn't set any username
        // we should set default username for him
        if (newAccount.username.isEmpty()) {
            newAccount = Account(
                account.id,
                account.email,
                Account.getDefaultUsername(account.email)
            )
            accountRepo.save(newAccount)
        }
        _currentAccount.postValue(newAccount)
        updateFriends()
        loading = false
    }
}
