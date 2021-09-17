package com.example.pokeme.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pokeme.data.models.Account
import io.reactivex.rxjava3.core.Flowable

@Dao
interface AccountDao{
    @Query("SELECT * FROM account")
    fun getAll() : Flowable<List<Account>>

    @Query("SELECT * FROM account WHERE id=:accountId")
    fun getById(accountId: String)

    @Insert
    fun insertAll(vararg accounts: Account)

    @Delete
    fun delete(account: Account)
}
