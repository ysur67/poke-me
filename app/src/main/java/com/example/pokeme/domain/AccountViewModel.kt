package com.example.pokeme.domain


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeme.data.models.Account
import com.example.pokeme.data.repository.AccountRepository
import com.example.pokeme.utils.Result
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
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

    fun getAccountFromRemote(account: Account) {
        loading = true
        accountRepo.getOrCreateAccount(account)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError {
                currentException = it as Exception
            }
            .subscribe {
                when (it) {
                    is Result.Success -> onAccountRetrieved(it.data)
                    is Result.Error -> currentException = it.ex
                }
            }
    }

    fun updateFriends() {
        val currentAccount = _currentAccount.value ?: return
        accountRepo.getFriends(currentAccount)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {

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
        loading = false
    }
}
