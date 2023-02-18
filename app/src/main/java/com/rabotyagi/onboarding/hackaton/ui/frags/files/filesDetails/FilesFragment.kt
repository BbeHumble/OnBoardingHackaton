package com.rabotyagi.onboarding.hackaton.ui.frags.files.filesDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rabotyagi.onboarding.hackaton.R
import com.rabotyagi.onboarding.hackaton.databinding.FragmentFileDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilesFragment : Fragment() {
    private var _binding: FragmentFileDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = arguments?.getString(getString(R.string.URL_KEY))
        if (url != null) {
            binding.webView.loadUrl(url)
        }
    }
}