package com.example.myapplication1.preferencesDataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "sampledatastore")

class DataStoreManager(private val context: Context) {

    suspend fun saveExampleData(ISLOGGINED: Boolean, USERID: Int, NAME: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ISLOGGINED] = ISLOGGINED
            preferences[PreferencesKeys.USERID] = USERID
            preferences[PreferencesKeys.NAME] = NAME
        }
    }


    // streming flow and live
    val ISLOGGINEDFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.ISLOGGINED] ?: false
        }

    val USERIDFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USERID] ?: 0
        }

    val NAMEFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.NAME] ?: ""
        }


    // Synchronous reads
    suspend fun getISLOGGINEDBoolean(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[PreferencesKeys.ISLOGGINED] ?: false
    }

    suspend fun getUSERID(): Int {
        val preferences = context.dataStore.data.first()
        return preferences[PreferencesKeys.USERID] ?: 0
    }

    suspend fun getUserName(): String {
        val preferences = context.dataStore.data.first()
        return preferences[PreferencesKeys.NAME] ?: ""
    }
}
