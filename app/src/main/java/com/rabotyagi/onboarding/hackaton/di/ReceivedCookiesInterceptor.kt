package com.rabotyagi.onboarding.hackaton.di

import com.rabotyagi.onboarding.hackaton.data.settings.UserSettings
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor(
    private val userSettings: UserSettings,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
                userSettings.setUserToken(header)
            }
        }
        return originalResponse
    }
}