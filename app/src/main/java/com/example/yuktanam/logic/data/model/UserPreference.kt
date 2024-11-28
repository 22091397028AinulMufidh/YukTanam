package com.example.yuktanam.logic.data.model

import androidx.datastore.core.DataStore
import java.util.prefs.Preferences

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

//    companion object {
//        @Volatile
//        private var INSTANCE: UserPreference? = null
//
//        private val AUTH_KEY = stringPreferencesKey("auth")
//        private val STATE_KEY = booleanPreferencesKey("state")
//
//        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
//            return INSTANCE ?: synchronized(this) {
//                val instance = UserPreference(dataStore)
//                INSTANCE = instance
//                instance
//            }
//        }
//        fun setToken(token: String) {
//            ApiConfig.setToken(token)
//        }
//
//    }
//
//    fun getUser(): Flow<UserModel> {
//        return dataStore.data.map { preferences ->
//            UserModel(
//                preferences[AUTH_KEY] ?:"",
//                preferences[STATE_KEY] ?: false
//            )
//        }
//    }
//
//    suspend fun saveUser(user: UserModel) {
//        dataStore.edit { preferences ->
//            preferences[AUTH_KEY] = user.tokenAuth
//            preferences[STATE_KEY] = user.isLogin
//        }
//    }
//
//    suspend fun logout() {
//        dataStore.edit { pref ->
//            pref[AUTH_KEY] = ""
//            pref[STATE_KEY]= false
//        }
//    }
}