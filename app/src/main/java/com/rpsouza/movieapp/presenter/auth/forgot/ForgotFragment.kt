package com.rpsouza.movieapp.presenter.auth.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rpsouza.movieapp.databinding.FragmentForgotBinding

class ForgotFragment : Fragment() {
  private var _binding: FragmentForgotBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentForgotBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}