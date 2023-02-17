package com.rabotyagi.onboarding.hackaton.ui.frags.login

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rabotyagi.onboarding.hackaton.R
import com.rabotyagi.onboarding.hackaton.databinding.FragmentLoginBinding
import com.rabotyagi.onboarding.hackaton.ui._global.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val loginViewModel by viewModels<LoginViewModel>()

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
            loading.subscribe {
                renderLoading(it)
            }.disposeOnPause()
            data.subscribe {
                findNavController().navigate(R.id.viewFragment)
                Toast.makeText(requireContext(), "Успех", Toast.LENGTH_SHORT).show()
            }.disposeOnPause()
            error.subscribe {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }.disposeOnPause()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}