package com.example.halanchallenge.utils.extensions

import android.content.Context
import android.net.ConnectivityManager
import androidx.datastore.preferences.preferencesDataStore
import com.example.halanchallenge.utils.USER_PREFERENCES_NAME


val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

