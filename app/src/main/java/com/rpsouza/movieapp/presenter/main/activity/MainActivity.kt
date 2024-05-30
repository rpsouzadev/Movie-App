package com.rpsouza.movieapp.presenter.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    installSplashScreen()
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    initNavigation()
  }

  private fun initNavigation() {
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController

    NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

    navController.addOnDestinationChangedListener { _, destination, _ ->
      binding.bottomNavigationView.isVisible =
        destination.id == R.id.menu_home ||
        destination.id == R.id.menu_search ||
        destination.id == R.id.menu_favorite ||
        destination.id == R.id.menu_download ||
        destination.id == R.id.menu_profile
    }
  }

}