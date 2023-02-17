package com.rabotyagi.onboarding.hackaton.ui.frags.login

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rabotyagi.onboarding.hackaton.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val loginViewModel by viewModels<LoginViewModel>()
    private val disposeOnPauseDisposables = CompositeDisposable()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    Toast.makeText(requireContext(), "Успех", Toast.LENGTH_SHORT).show()
                },
                error.subscribe {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                },
            )
        }
    }

    override fun onPause() {
        super.onPause()
        disposeOnPauseDisposables.clear()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}