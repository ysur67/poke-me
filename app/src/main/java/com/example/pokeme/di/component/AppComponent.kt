package com.example.pokeme.di.component

import android.app.Application
import android.content.Context
import com.example.pokeme.di.module.AppModule
import com.example.pokeme.di.module.RepositoryModule
import com.example.pokeme.di.module.ViewModelModule
import com.example.pokeme.presentation.activity.AuthActivity
import com.example.pokeme.presentation.activity.MainActivity
import com.example.pokeme.presentation.fragment.auth.LoginFragment
import com.example.pokeme.presentation.fragment.auth.RegisterFragment
import com.example.pokeme.repository.AccountRepository
import com.example.pokeme.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Component
import javax.inject.Singleton


@Component(modules=[
    AppModule::class,
    RepositoryModule::class,
])
@Singleton
interface AppComponent {
    fun inject(authActivity: AuthActivity)
    fun inject(mainActivity: MainActivity)

    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
}
