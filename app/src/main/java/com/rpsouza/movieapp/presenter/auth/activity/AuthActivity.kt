package com.rpsouza.movieapp.presenter.auth.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
  private lateinit var binding: ActivityAuthBinding

  private lateinit var navController: NavController
  private lateinit var navHostFragment: NavHostFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAuthBinding.inflate((layoutInflater))
    setContentView(binding.root)

    setStatusBarTranslucent()
    initNavigation()
  }

  private fun initNavigation() {
    navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController

    navController.addOnDestinationChangedListener { _, destination, _ ->
      if(destination.id != R.id.onboardingFragment) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      }
    }
  }

  private fun setStatusBarTranslucent() {
    window.decorView.systemUiVisibility =
      View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
          View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
          View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
  }
}