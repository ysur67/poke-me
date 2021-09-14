package com.example.pokeme.repository


import com.example.pokeme.data.models.Account
import com.example.pokeme.utils.Result
import com.example.pokeme.utils.firebase.getStringOrEmpty
import com.example.pokeme.utils.firebase.isNotEmpty
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class AccountRepositoryFirebase @Inject constructor(
    private val connection: FirebaseFirestore
    ) : AccountRepository {

    override fun getOrCreateAccount(user: FirebaseUser) : Observable<Result<Account>> {
        val email = user.email!!
        return Observable.create{ emitter ->
            connection.collection(ACCOUNT_COLLECTION).document(email).get()
                .addOnSuccessListener {
                    val username = it.getStringOrEmpty("username")
                    emitter.onNext(Result.Success(Account(email, username)))
                }
                .addOnFailureListener {
                    emitter.onNext(Result.Error(it))
                }
        }
    }

    override fun updateDocument(id: String, fields: HashMap<String, String>) {
        connection.collection(ACCOUNT_COLLECTION).document(id).set(fields)
    }

    override fun getFriends(account: Account) : Observable<Result<List<Account>>> {
        return Observable.create {
            connection.collection(FRIENDS_COLLECTION)
                .whereEqualTo("firstAccount", account.email)
                .addSnapshotListener{ snapshot, error ->
                    if (error != null) {
                        it.onError(error)
                        return@addSnapshotListener
                    }
                    val friends = ArrayList<Account>()
                    if (snapshot.isNotEmpty) {
                        for (document in snapshot!!) {
                            val email = document.getStringOrEmpty("secondAccount")
                            friends.add(Account(email, ""))
                        }
                    }
                    it.onNext(Result.Success(friends))
                }
        }
    }

    companion object {
        private const val ACCOUNT_COLLECTION: String = "accounts"
        private const val FRIENDS_COLLECTION: String = "friends"
    }
}
