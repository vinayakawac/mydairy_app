package com.example.mydairy_app.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.appPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    val darkModeOverride: Flow<Boolean?> = context.appPreferencesDataStore.data
        .catch { throwable ->
            if (throwable is IOException) {
                emit(emptyPreferences())
            } else {
                throw throwable
            }
        }
        .map { preferences ->
            preferences[DARK_MODE_OVERRIDE_KEY]
        }

    suspend fun setDarkModeOverride(value: Boolean?): Unit {
        context.appPreferencesDataStore.edit { preferences ->
            if (value == null) {
                preferences.remove(DARK_MODE_OVERRIDE_KEY)
            } else {
                preferences[DARK_MODE_OVERRIDE_KEY] = value
            }
        }
    }

    private companion object {
        const val DATASTORE_NAME: String = "app_preferences"
        val DARK_MODE_OVERRIDE_KEY: Preferences.Key<Boolean> = booleanPreferencesKey("dark_mode_override")
    }
}
