package com.example.pokeme.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokeme.data.PokeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DataSourceModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : PokeDatabase {
        return Room.databaseBuilder(
            context,
            PokeDatabase::class.java,
            "poke-database"
        ).build()
    }
}