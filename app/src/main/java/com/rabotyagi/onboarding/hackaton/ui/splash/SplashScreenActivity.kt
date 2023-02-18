package com.rabotyagi.onboarding.hackaton.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rabotyagi.onboarding.hackaton.data.settings.UserSettings
import com.rabotyagi.onboarding.hackaton.ui.container.ContainerActivity
import com.rabotyagi.onboarding.hackaton.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var userSettings: UserSettings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (userSettings.getUserToken() == null)
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
        else startActivity(Intent(this@SplashScreenActivity, ContainerActivity::class.java))
        finish()
    }
}