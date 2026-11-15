package com.example.githubandroidclient.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.githubandroidclient.dataStore

class KeyValueStorage (private val context: Context){
    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    suspend fun setToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }
}