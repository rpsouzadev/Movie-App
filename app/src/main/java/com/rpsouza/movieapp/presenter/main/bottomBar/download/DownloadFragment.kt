package com.rpsouza.movieapp.presenter.main.bottomBar.download

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentDownloadBinding

class DownloadFragment : Fragment() {
  private var _binding: FragmentDownloadBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDownloadBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}