package com.rabotyagi.onboarding.hackaton.data.settings

import android.content.SharedPreferences

class UserSettings (private val sharedPreferences: SharedPreferences) {
    companion object{
        const val AUTH_TOKEN_KEY = "auth token key"
    }
    private var oAuthToken: String? = sharedPreferences.getString(AUTH_TOKEN_KEY, null)

    fun setUserToken(userToken: String?) {
        oAuthToken = userToken
        saveUserAuthToken(userToken = userToken)
    }

    private fun saveUserAuthToken(userToken: String?) {
        sharedPreferences.edit().putString(AUTH_TOKEN_KEY, userToken).apply()
    }

    fun logOut(){
        oAuthToken = null
        saveUserAuthToken(oAuthToken)
    }

    fun getUserToken() = oAuthToken


}