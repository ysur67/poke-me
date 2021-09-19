package com.example.pokeme.di.module

import com.example.pokeme.data.repository.AccountRepository
import com.example.pokeme.data.repository.MessageRepository
import com.example.pokeme.data.repository.implementation.AccountRepositoryImpl
import com.example.pokeme.data.repository.implementation.AuthRepositoryImpl
import com.example.pokeme.data.repository.AuthRepository
import com.example.pokeme.data.repository.implementation.MessagesRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [DataSourceModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAccountRepository(firestore: FirebaseFirestore) : AccountRepository {
        return AccountRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firebaseAuth: FirebaseAuth) : AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(
        firestore: FirebaseFirestore,
        firebaseMessaging: FirebaseMessaging
    ) : MessageRepository {
        return MessagesRepositoryImpl(firestore, firebaseMessaging)
    }
}
