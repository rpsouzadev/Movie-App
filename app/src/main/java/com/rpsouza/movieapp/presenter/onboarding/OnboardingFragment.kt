package com.rpsouza.movieapp.presenter.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentOnboardingBinding
import com.rpsouza.movieapp.utils.animNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
  private var _binding: FragmentOnboardingBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initListeners()
  }

  private fun initListeners() {
    binding.btnStart.setOnClickListener {
      val action = R.id.action_onboardingFragment_to_Authentication
      findNavController().animNavigate(action)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}