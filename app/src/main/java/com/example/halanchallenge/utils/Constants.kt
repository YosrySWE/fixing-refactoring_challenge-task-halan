package com.example.halanchallenge.utils

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph

const val BASE_URL = "https://assessment-sn12.halan.io"

private const val DATASTORE_PREFERENCES_NAME = "auth_preferences"

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = DATASTORE_PREFERENCES_NAME)


fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun NavController.safeNavigate(
    @IdRes currentDestinationId: Int,
    @IdRes id: Int,
    args: Bundle? = null
) {
    if (currentDestinationId == currentDestination?.id) {
        navigate(id, args)
    }
}