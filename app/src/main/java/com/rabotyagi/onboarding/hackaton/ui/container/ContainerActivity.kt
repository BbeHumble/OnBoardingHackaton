package com.rabotyagi.onboarding.hackaton.ui.container

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rabotyagi.onboarding.hackaton.R
import com.rabotyagi.onboarding.hackaton.data.settings.UserSettings
import com.rabotyagi.onboarding.hackaton.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContainerActivity :
    AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val profileViewModel by viewModels<ContainerViewModel>()
    private val binding get() = _binding!!

    @Inject
    lateinit var userSettings: UserSettings


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph =
            navController.navInflater.inflate(R.navigation.main_nav_graph)

        navGraph.setStartDestination(R.id.profileFragment)

        binding.navView.setupWithNavController(navController)
        binding.mainContent.bottomBar
            .setupWithNavController(navController)
        navController.graph = navGraph

    }

}