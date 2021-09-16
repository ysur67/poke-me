package com.example.pokeme.domain


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokeme.data.models.Account
import com.example.pokeme.repository.AccountRepository
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseUser
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

    fun updateAccountByUser(user: FirebaseUser) {
        loading = true
        accountRepo.getOrCreateAccount(user)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError {
                currentException = it as Exception
            }
            .subscribe{
                when (it) {
                    is Result.Success -> {
                        _currentAccount.postValue(it.data)
                        loading = false
                    }
                    is Result.Error -> {
                        currentException = it.ex
                    }
                }
            }
    }

    fun updateAccount(account: Account) {
        loading = true
        accountRepo.updateDocument(account.email, account.toHashMap())
        _currentAccount.postValue(account)
        loading = false
    }

    fun updateFriends() {
        val currentAccount = _currentAccount.value ?: return
        accountRepo.getFriends(currentAccount)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe{

            }
    }
}
