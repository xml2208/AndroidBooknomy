package com.example.androidbooknomy.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object PreferencesKeys {
    val token = stringPreferencesKey("token_key")
}

val Context.prefsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")