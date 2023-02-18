package com.rabotyagi.onboarding.hackaton

import android.app.Application
import com.onesignal.OneSignal


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications()
    }

    companion object {
        private const val ONESIGNAL_APP_ID = "a96f69ae-049e-4a93-b2a7-728ffdf3e047"
    }
}