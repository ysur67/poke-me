package com.example.pokeme.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokeme.di.ViewModelFactory
import com.example.pokeme.di.ViewModelKey
import com.example.pokeme.domain.AccountViewModel
import com.example.pokeme.domain.MessageViewModel
import com.example.pokeme.domain.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ) : ViewModelProvider.Factory

    @Binds
    @Singleton
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(viewModel: AccountViewModel) : ViewModel

    @Binds
    @Singleton
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserViewModel) : ViewModel

    @Binds
    @Singleton
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindMessageViewModel(viewModel: UserViewModel) : ViewModel
}
