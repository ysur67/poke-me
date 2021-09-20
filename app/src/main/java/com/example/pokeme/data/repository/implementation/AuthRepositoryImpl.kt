package com.example.pokeme.data.repository.implementation

import com.example.pokeme.data.mapper.toAccount
import com.example.pokeme.data.models.Account
import com.example.pokeme.data.repository.AuthRepository
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.lang.NullPointerException
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    val authService: FirebaseAuth
    ) : AuthRepository {

    override val currentAccount: Account?
        get() {
            val user = authService.currentUser ?: return null
            return Account.toAccount(user)
        }


    override suspend fun register(email: String, password: String) : Result<Account> {
        lateinit var result: Result<Account>
        authService.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = it.user
                if (user != null) {
                    result = Result.Success(Account.toAccount(user))
                    return@addOnSuccessListener
                }
                result = Result.Error(NullPointerException("There is no user with such login data"))
            }
            .addOnFailureListener {
                result = Result.Error(it)
            }
            .await()
        return result
    }

    override suspend fun login(email: String, password: String) : Result<Account> {
        lateinit var result: Result<Account>
        authService.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val user = it.user
                if (user != null) {
                    result = Result.Success(Account.toAccount(user))
                    return@addOnSuccessListener
                }
                result = Result.Error(NullPointerException("There is no user with such login data"))
            }
            .addOnFailureListener {
                result = Result.Error(it)
            }
            .await()
        return result
    }

    override fun logout() {
        authService.signOut()
    }

    companion object {
        private const val DEBUG_CODE: String = "USER_REPO"
    }
}
