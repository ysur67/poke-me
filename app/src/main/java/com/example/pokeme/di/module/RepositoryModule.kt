package com.example.pokeme.di.module

import com.example.pokeme.repository.AccountRepository
import com.example.pokeme.repository.AccountRepositoryFirebase
import com.example.pokeme.repository.UserRepository
import com.example.pokeme.repository.UserRepositoryFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.annotation.Signed
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
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAccountRepository(firestore: FirebaseFirestore) : AccountRepository {
        return AccountRepositoryFirebase(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firebaseAuth: FirebaseAuth) : UserRepository {
        return UserRepositoryFirebase(firebaseAuth)
    }
}