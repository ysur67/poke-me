package com.example.pokeme.data.repository.implementation


import com.example.pokeme.data.mapper.toAccount
import com.example.pokeme.data.models.Account
import com.example.pokeme.data.repository.AccountRepository
import com.example.pokeme.utils.Result
import com.example.pokeme.utils.firebase.getStringOrEmpty
import com.example.pokeme.utils.firebase.isNotEmpty
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(
    private val connection: FirebaseFirestore
    ) : AccountRepository {

    override suspend fun getOrCreateAccount(account: Account) : Result<Account> {
        val email = account.email
        lateinit var result: Result<Account>
        connection.collection(ACCOUNT_COLLECTION).document(email).get()
            .addOnSuccessListener { document ->
                result = Result.Success(Account.toAccount(document))
            }
            .addOnFailureListener {
                result = Result.Error(it)
            }
            .await()
        return result
    }

    override fun updateDocument(id: String, fields: HashMap<String, String>) {
        connection.collection(ACCOUNT_COLLECTION).document(id).set(fields)
    }

    override suspend fun getFriends(account: Account): Result<List<Account>> {
        // TODO: make it better
        lateinit var result: Result<List<Account>>
        connection.collection(FRIENDS_COLLECTION).whereEqualTo("firstAccount", account.email)
                .get()
                .addOnSuccessListener {
                    if (it.isNotEmpty) {
                        val friends = ArrayList<Account>()
                        it?.map {
                            val email = it.getStringOrEmpty("secondAccount")
                            friends.add(Account(it.id, email, ""))
                        }
                        result = Result.Success(friends)
                    }
                }
                .await()
        return result
    }

    override fun save(account: Account) {
        connection.collection(ACCOUNT_COLLECTION).document(account.email).set(account.toHashMap())
    }

    companion object {
        private const val ACCOUNT_COLLECTION: String = "accounts"
        private const val FRIENDS_COLLECTION: String = "friends"
    }
}
