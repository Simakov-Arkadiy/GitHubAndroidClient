package com.example.githubandroidclient

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubandroidclient.di.diModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(diModule)
        }
    }
}
val Context.dataStore by preferencesDataStore(name = "token")