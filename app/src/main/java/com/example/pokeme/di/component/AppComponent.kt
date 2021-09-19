package com.example.pokeme.di.component

import com.example.pokeme.di.module.AppModule
import com.example.pokeme.di.module.RepositoryModule
import com.example.pokeme.di.module.ViewModelModule
import com.example.pokeme.presentation.activity.AuthActivity
import com.example.pokeme.presentation.activity.MainActivity
import com.example.pokeme.presentation.fragment.profile.FriendListFragment
import com.example.pokeme.presentation.fragment.PokesFragment
import com.example.pokeme.presentation.fragment.profile.ProfileFragment
import com.example.pokeme.presentation.fragment.profile.ProfileSettingsFragment
import com.example.pokeme.presentation.fragment.auth.LoginFragment
import com.example.pokeme.presentation.fragment.auth.RegisterFragment
import com.example.pokeme.service.FirebasePokesService
import dagger.Component
import javax.inject.Singleton


@Component(modules=[
    AppModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
@Singleton
interface AppComponent {
    fun inject(authActivity: AuthActivity)
    fun inject(mainActivity: MainActivity)

    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(friendListFragment: FriendListFragment)
    fun inject(pokesFragment: PokesFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(profileSettingsFragment: ProfileSettingsFragment)

    fun inject(firebasePokesService: FirebasePokesService)
}
