package com.example.rescue4x4.sos

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class StoreData(private val context: Context) {
    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_PHONE = stringPreferencesKey("user_phone")
    }

    suspend fun saveUserData(name: String, phone: String){
        context.dataStore.edit {
            it[USER_NAME] = name
            it[USER_PHONE] = phone
        }
        Log.d("storedata", "$name, $phone")
    }

    val userNameFlow: Flow<String> = context.dataStore.data.map {
        it[USER_NAME] ?: ""
    }
    val userPhoneFlow: Flow<String> = context.dataStore.data.map {
        it[USER_PHONE] ?: ""
    }
}