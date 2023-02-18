package com.rabotyagi.onboarding.hackaton.data.settings

import android.content.SharedPreferences
import javax.inject.Inject

class UserSettings @Inject constructor (private val sharedPreferences: SharedPreferences) {
    companion object{
        const val AUTH_TOKEN_KEY = "auth token key"
        const val FIREBASE_TOKEN = "firebase token key"
    }
    private var oAuthToken: String? = sharedPreferences.getString(AUTH_TOKEN_KEY, null)
    private var firebaseToken: String? = sharedPreferences.getString(FIREBASE_TOKEN, null)

    fun setUserToken(userToken: String?) {
        oAuthToken = userToken
        saveUserAuthToken(userToken = userToken)
    }

    fun setFcmToken(token: String?) {
        firebaseToken = token
        saveUserFcm(token = token)
    }

    private fun saveUserAuthToken(userToken: String?) {
        sharedPreferences.edit().putString(AUTH_TOKEN_KEY, userToken).apply()
    }

    private fun saveUserFcm(token: String?) {
        sharedPreferences.edit().putString(FIREBASE_TOKEN, token).apply()
    }

    fun logOut(){
        oAuthToken = null
        saveUserAuthToken(oAuthToken)
    }

    fun getUserToken() = oAuthToken
    fun getFcmToken() = firebaseToken


}