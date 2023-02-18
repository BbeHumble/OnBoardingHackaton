package com.rabotyagi.onboarding.hackaton.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rabotyagi.onboarding.hackaton.ui.container.ContainerActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashScreenActivity, ContainerActivity::class.java))
        finish()
    }
}