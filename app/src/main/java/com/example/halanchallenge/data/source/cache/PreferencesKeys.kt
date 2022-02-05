package com.example.halanchallenge.data.source.cache

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val PASSWORD = stringPreferencesKey("password")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val TOKEN_KEY = stringPreferencesKey("token")
}