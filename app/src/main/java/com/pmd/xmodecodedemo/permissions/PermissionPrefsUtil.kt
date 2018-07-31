package com.pmd.xmodecodedemo.permissions

import android.content.Context
import android.content.Context.MODE_PRIVATE

const val PERMISSION_PREFS = "PERMISSION_PREFS"

/**
 * Saves result of permissions request so user is not repeatedly asked
 */
fun onFirstTimeRequestingPermission(context: Context, permission: String, isFirst: Boolean) {
    val preferences = context.getSharedPreferences(PERMISSION_PREFS, MODE_PRIVATE)
    preferences.edit().putBoolean(permission, isFirst).apply()
}

/**
 * Checks whether user has already been asked for permission
 */
fun isFirstTimeRequestingPermission(context: Context, permission: String): Boolean {
    return context.getSharedPreferences(PERMISSION_PREFS, MODE_PRIVATE).getBoolean(permission, true)
}