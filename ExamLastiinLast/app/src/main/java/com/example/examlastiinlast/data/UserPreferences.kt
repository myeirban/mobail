package com.example.examlastiinlast.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {
    companion object {
        val SHOW_MONGOLIAN = booleanPreferencesKey("show_mongolian")
        val SHOW_FOREIGN = booleanPreferencesKey("show_foreign")
    }

    val showMongolian: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHOW_MONGOLIAN] ?: true
        }

    val showForeign: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHOW_FOREIGN] ?: true
        }

    suspend fun updateShowMongolian(show: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_MONGOLIAN] = show
        }
    }

    suspend fun updateShowForeign(show: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_FOREIGN] = show
        }
    }
} 