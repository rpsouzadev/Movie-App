package com.rpsouza.movieapp.presenter.main.movieGenre

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentMovieGenreBinding
import com.rpsouza.movieapp.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieGenreFragment : Fragment() {
  private var _binding: FragmentMovieGenreBinding? = null
  private val binding get() = _binding!!

  private val args: MovieGenreFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMovieGenreBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initToolbar(toolbar = binding.toolbar)
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}