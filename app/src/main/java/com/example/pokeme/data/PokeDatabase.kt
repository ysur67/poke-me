package com.example.pokeme.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokeme.data.dao.AccountDao
import com.example.pokeme.data.models.Account

@Database(entities = [Account::class], version = 1)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun accountDao() : AccountDao
}
