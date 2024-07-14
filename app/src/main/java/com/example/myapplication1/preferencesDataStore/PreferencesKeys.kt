package com.example.myapplication1.preferencesDataStore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {

    val ISLOGGINED = booleanPreferencesKey("ISLOGGINED")
    val USERID = intPreferencesKey("USERID")
    val NAME = stringPreferencesKey("NAME")


}