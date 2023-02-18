package com.rabotyagi.onboarding.hackaton.core

import android.app.NotificationManager
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rabotyagi.onboarding.hackaton.R
import com.rabotyagi.onboarding.hackaton.data.api.ApiService
import com.rabotyagi.onboarding.hackaton.data.settings.UserSettings
import com.rabotyagi.onboarding.hackaton.schedule.SchedulersProvider
import com.rabotyagi.onboarding.hackaton.ui.container.ContainerActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var registerTokenDisposable: Disposable? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        sendNotification(remoteMessage.notification?.body)
    }

    @Inject
    lateinit var userSettings: UserSettings

    override fun onNewToken(token: String) {
        userSettings.setFcmToken(token)
        /*  registerTokenDisposable?.dispose()
          registerTokenDisposable = apiService.registerFCMToken(token)
              .subscribeOn(schedulers.io())
              .observeOn(schedulers.ui())
              .subscribe(
                  {},
                  {}
              )*/
    }

    override fun onDestroy() {
        registerTokenDisposable?.dispose()
        super.onDestroy()
    }

    private fun sendNotification(messageBody: String?) {
        val intent = Intent(this, ContainerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setContentTitle(this.getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}