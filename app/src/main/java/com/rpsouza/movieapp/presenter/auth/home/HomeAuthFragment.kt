package com.rpsouza.movieapp.presenter.auth.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentHomeAuthBinding

class HomeAuthFragment : Fragment() {
  private var _binding: FragmentHomeAuthBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentHomeAuthBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}