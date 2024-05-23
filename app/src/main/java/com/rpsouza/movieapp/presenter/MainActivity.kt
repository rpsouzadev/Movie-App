package com.rpsouza.movieapp.presenter

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.ActivityMainBinding
import com.rpsouza.movieapp.presenter.auth.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val registerFragment = RegisterFragment()

    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.add(R.id.container, registerFragment).commit()
  }


}