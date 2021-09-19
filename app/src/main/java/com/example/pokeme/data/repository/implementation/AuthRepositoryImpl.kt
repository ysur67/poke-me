package com.example.pokeme.data.repository.implementation

import com.example.pokeme.data.mapper.toAccount
import com.example.pokeme.data.models.Account
import com.example.pokeme.data.repository.AuthRepository
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    val authService: FirebaseAuth
    ) : AuthRepository {

    override val currentAccount: Account?
        get() {
            val user = authService.currentUser ?: return null
            return Account.toAccount(user)
        }


    override fun register(email: String, password: String) : Observable<Result<Account>> {
        return Observable.create{ emitter ->
            val task = authService.createUserWithEmailAndPassword(email, password)
            task.addOnSuccessListener {
                val user = it.user
                if (user != null) {
                    emitter.onNext(Result.Success(Account.toAccount(user)))
                    return@addOnSuccessListener
                }
                emitter.onNext(Result.Error(NullPointerException("Registration error")))
            }
            task.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun login(email: String, password: String) : Observable<Result<Account>> {
        return Observable.create{ emitter ->
            val task = authService.signInWithEmailAndPassword(email, password)
            task.addOnSuccessListener {
                val user = it.user
                if (user != null) {
                    emitter.onNext(Result.Success(Account.toAccount(user)))
                } else {
                    emitter.onError(FirebaseAuthException(
                        "101",
                        "There is no user with such login data"
                    ))
                }
            }
            task.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun logout() {
        authService.signOut()
    }

    companion object {
        private const val DEBUG_CODE: String = "USER_REPO"
    }
}
