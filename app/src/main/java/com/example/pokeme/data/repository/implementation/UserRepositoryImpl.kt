package com.example.pokeme.data.repository.implementation

import com.example.pokeme.data.repository.UserRepository
import com.example.pokeme.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    val authService: FirebaseAuth
    ) : UserRepository {

    override val user: FirebaseUser?
        get() = authService.currentUser

    override fun register(email: String, password: String) : Observable<Result<FirebaseUser>> {
        return Observable.create{ emitter ->
            val task = authService.createUserWithEmailAndPassword(email, password)
            task.addOnSuccessListener {
                val user = it.user
                if (user != null) {
                    emitter.onNext(Result.Success(user))
                    return@addOnSuccessListener
                }
                emitter.onNext(Result.Error(NullPointerException("Registration error")))
            }
            task.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun login(email: String, password: String) : Observable<Result<FirebaseUser>> {
        return Observable.create{ emitter ->
            val task = authService.signInWithEmailAndPassword(email, password)
            task.addOnSuccessListener {
                val user = it.user
                if (user != null) {
                    emitter.onNext(Result.Success(user))
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
