package com.leodeleon.favqs.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

data class AppPrefs(
    val token: String,
)

interface IPrefsRepo {
    val appPrefsFlow: Flow<AppPrefs>

    suspend fun setToken(token: String)
}

class PrefsRepo(private val dataStore: DataStore<Preferences>) : IPrefsRepo {
    private companion object {
        const val TAG = "SettingsRepo"
    }

    private object Keys {
        val token = stringPreferencesKey("token")
    }

    private val moshi = Moshi.Builder().build()
    override val appPrefsFlow: Flow<AppPrefs> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapPreferences(preferences)
        }

    override suspend fun setToken(token: String) {
        dataStore.edit { prefs ->
            prefs[Keys.token] = token
        }
    }

    private fun mapPreferences(preferences: Preferences): AppPrefs {
        return AppPrefs(
            token = preferences[Keys.token].orEmpty(),
        )
    }
}

