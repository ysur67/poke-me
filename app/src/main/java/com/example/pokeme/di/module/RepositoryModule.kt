package com.example.pokeme.di.module

import com.example.pokeme.data.repository.AccountRepository
import com.example.pokeme.data.repository.MessageRepository
import com.example.pokeme.data.repository.implementation.AccountRepositoryImpl
import com.example.pokeme.data.repository.implementation.UserRepositoryImpl
import com.example.pokeme.data.repository.UserRepository
import com.example.pokeme.data.repository.implementation.MessagesRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

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

    @Provides
    @Singleton
    fun provideAccountRepository(firestore: FirebaseFirestore) : AccountRepository {
        return AccountRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firebaseAuth: FirebaseAuth) : UserRepository {
        return UserRepositoryImpl(firebaseAuth)
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
