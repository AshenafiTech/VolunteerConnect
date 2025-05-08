package com.mobile.volunteerconnect.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferences @Inject constructor(private val context: Context) {

    companion object {
        val TOKEN = stringPreferencesKey("token")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    suspend fun saveUserData(token: String, name: String, email: String, role: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN] = token
            prefs[USER_NAME] = name
            prefs[USER_EMAIL] = email
            prefs[USER_ROLE] = role
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun getToken(): String? = context.dataStore.data.map { it[TOKEN] }.first()
}
