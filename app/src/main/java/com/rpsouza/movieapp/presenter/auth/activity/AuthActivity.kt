package com.rpsouza.movieapp.presenter.auth.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.ActivityAuthBinding
import com.rpsouza.movieapp.presenter.auth.enums.AuthenticationDestinations
import com.rpsouza.movieapp.presenter.main.activity.MainActivity
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.getSerializableCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate((layoutInflater))
        setContentView(binding.root)
        initNavigation()
        isAuthenticated()
    }

    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val graph = navController.navInflater.inflate(R.navigation.auth_graph)
        graph.setStartDestination(getDestination())
        navController.graph = graph

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.onboardingFragment) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    private fun isAuthenticated() {
        if (FirebaseHelper.isAuthenticated()) {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }

    private fun getDestination(): Int {
        val destination =
            intent.getSerializableCompat<AuthenticationDestinations>(AUTHENTICATION_PARAMETER)

        return when (destination) {
            AuthenticationDestinations.LOGIN_SCREEN -> {
                R.id.Authentication
            }

            else -> {
                R.id.splashFragment
            }
        }

    }

    companion object {
        const val AUTHENTICATION_PARAMETER = "AUTHENTICATION_PARAMETER"
    }
}