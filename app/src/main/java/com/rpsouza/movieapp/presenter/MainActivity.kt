package com.rpsouza.movieapp.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.ActivityMainBinding
import com.rpsouza.movieapp.presenter.auth.forgot.ForgotFragment
import com.rpsouza.movieapp.presenter.auth.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    installSplashScreen()
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val forgotFragment = ForgotFragment()

    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.add(R.id.container, forgotFragment).commit()
  }


}