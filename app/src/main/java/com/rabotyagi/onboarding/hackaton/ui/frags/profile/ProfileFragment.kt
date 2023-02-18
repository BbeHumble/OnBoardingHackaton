package com.rabotyagi.onboarding.hackaton.ui.frags.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rabotyagi.onboarding.hackaton.data.settings.UserSettings
import com.rabotyagi.onboarding.hackaton.databinding.FragmentProfileBinding
import com.rabotyagi.onboarding.hackaton.ui._global.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var userSettings: UserSettings


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            accountName.text = "${userSettings.userInfo?.name} ${userSettings.userInfo?.lastName}"
            accountEmail.text = userSettings.userInfo?.username
            accountRole.text = userSettings.userInfo?.role?.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}