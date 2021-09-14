package com.example.pokeme

import android.app.Application
import com.example.pokeme.di.component.AppComponent
import com.example.pokeme.di.component.DaggerAppComponent
import com.example.pokeme.di.module.AppModule
import com.google.firebase.FirebaseApp

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}