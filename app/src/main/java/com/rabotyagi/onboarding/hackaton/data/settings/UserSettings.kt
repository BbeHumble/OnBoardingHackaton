package com.rabotyagi.onboarding.hackaton.data.settings

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rabotyagi.onboarding.hackaton.data.model.login.UserInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserSettings @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    companion object {
        const val AUTH_TOKEN_KEY = "auth token key"
        private val USER_INFO_DATA = "user info data"
        const val USER_INFO = "user info"
    }

    private fun getSharedPreferences(prefsName: String) =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    private var oAuthToken: String? = sharedPreferences.getString(AUTH_TOKEN_KEY, null)
    private val userPrefs by lazy { getSharedPreferences(USER_INFO) }

    var userInfo: UserInfo?
        get() = userPrefs
            .getString(USER_INFO_DATA, null)
            ?.let { gson.fromJson<UserInfo>(it, UserInfo::class.java) }
        set(value) {
            userPrefs.edit().putString(USER_INFO_DATA, gson.toJson(value)).apply()
        }

    fun setUserToken(userToken: String?) {
        oAuthToken = userToken
        saveUserAuthToken(userToken = userToken)
    }


    private fun saveUserAuthToken(userToken: String?) {
        sharedPreferences.edit().putString(AUTH_TOKEN_KEY, userToken).apply()
    }

    fun logOut() {
        oAuthToken = null
        saveUserAuthToken(oAuthToken)
    }

    fun getUserToken() = oAuthToken


}