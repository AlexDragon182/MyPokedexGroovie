package com.example.my_pokedex.localstorage

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.example.my_pokedex.utils.AndroidKeyStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferencesManager"

data class FilterPreferences(
    val token: String = "",
    val braintreeToken: String = ""
)

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore("user_preferences")

    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val token = preferences[PreferencesKeys.TOKEN] ?: ""
            val braintreeToken = preferences[PreferencesKeys.BRAINTREE_TOKEN] ?: ""

            FilterPreferences(AndroidKeyStore.decrypt(token), AndroidKeyStore.decrypt(braintreeToken))
        }

    suspend fun updateToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = AndroidKeyStore.encrypt(token)
        }
    }

    suspend fun updateBraintreeToken(braintreeToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BRAINTREE_TOKEN] = AndroidKeyStore.encrypt(braintreeToken)
        }
    }

    private object PreferencesKeys {
        val TOKEN = preferencesKey<String>("token")
        val BRAINTREE_TOKEN = preferencesKey<String>("braintree_token")
    }
}