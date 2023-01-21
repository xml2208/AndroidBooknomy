package com.example.androidbooknomy.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class Prefs(private val context: Context) {

    val token: String
        get() = runBlocking {
            context.prefsDataStore.data.map { it[PreferencesKeys.token] }.firstOrNull().orEmpty()
        }

    val isLoggedIn: Boolean
        get() = runBlocking {
            context.prefsDataStore.data.map { it[PreferencesKeys.token] != null }.firstOrNull() ?: false
        }

    suspend fun saveToken(token: String) {
        context.prefsDataStore.edit {
            it[PreferencesKeys.token] = token
        }
    }
}