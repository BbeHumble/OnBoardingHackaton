package com.rabotyagi.onboarding.hackaton.ui.login

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rabotyagi.onboarding.hackaton.databinding.ActivityLoginBinding
import com.rabotyagi.onboarding.hackaton.ui.container.ContainerActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val loginViewModel by viewModels<LoginViewModel>()
    private val disposeOnPauseDisposables = CompositeDisposable()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply {
            btnLogin.setOnClickListener {
                loginViewModel.login(etUsername.text.toString(), etPassword.text.toString())
            }
        }

    }

    override fun onResume() {
        super.onResume()
        loginViewModel.apply {
            disposeOnPauseDisposables.addAll(
                loading.subscribe {
                    renderLoading(it)
                },
                data.subscribe {
                    loginViewModel.getUserData()
                },
                userInfo.subscribe {
                    startActivity(Intent(this@LoginActivity, ContainerActivity::class.java))
                    finish()
                    Toast.makeText(this@LoginActivity, "Успех", Toast.LENGTH_SHORT).show()
                },
                error.subscribe {
                    Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
                })
        }
    }

    private fun renderLoading(show: Boolean?) {
        binding.apply {
            if (show == true) {
                loaderContainer.visibility = View.VISIBLE
                progressBar.repeatCount = ValueAnimator.INFINITE
                progressBar.playAnimation()
            } else {
                loaderContainer.visibility = View.INVISIBLE
                progressBar.pauseAnimation()
            }
        }
    }
}