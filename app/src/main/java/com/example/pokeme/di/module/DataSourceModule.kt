package com.example.pokeme.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokeme.data.PokeDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
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

    @Provides
    @Singleton
    fun provideFirestore() : FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideFirebaseMessaging() : FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}