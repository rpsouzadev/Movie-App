package com.rpsouza.movieapp.presenter.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSplashScreen()
    }

    private fun initSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
                run {
                    val action = R.id.action_splashFragment_to_onboardingFragment
                    findNavController().navigate(action)
                }
            }, 3000
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}