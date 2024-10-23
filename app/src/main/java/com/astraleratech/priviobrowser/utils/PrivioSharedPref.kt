package com.astraleratech.priviobrowser.utils


import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrivioSharedPref @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "PrivioPrefs"
        private const val LAST_TRANSACTED_TAB_TAG = "lastTransactedTabTag"
    }

    // Save lastTransactedTabTag in SharedPreferences
    fun setLastTransactedTabTag(tag: String) {
        sharedPreferences.edit().putString(LAST_TRANSACTED_TAB_TAG, tag).apply()
    }

    // Retrieve lastTransactedTabTag from SharedPreferences
    fun getLastTransactedTabTag(): String? {
        return sharedPreferences.getString(LAST_TRANSACTED_TAB_TAG, null)
    }
}
