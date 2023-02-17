package com.rabotyagi.onboarding.hackaton.ui.container

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rabotyagi.onboarding.hackaton.R
import com.rabotyagi.onboarding.hackaton.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerActivity:AppCompatActivity(R.layout.activity_main){
    private var _binding: ActivityMainBinding? = null
    private val profileViewModel by viewModels<ContainerViewModel>()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
        val navView: NavigationView = findViewById(R.id.nav_view)
//        navGraph.setStartDestination(R.id.profileFragment)
        navController.graph = navGraph
        navView.setupWithNavController(navController)
    }

}