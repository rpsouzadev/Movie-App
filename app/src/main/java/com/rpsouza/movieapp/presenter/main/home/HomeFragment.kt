package com.rpsouza.movieapp.presenter.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rpsouza.movieapp.databinding.FragmentHomeBinding
import com.rpsouza.movieapp.presenter.main.home.adapter.GenreMovieAdapter
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

  private val homeViewModel: HomeViewModel by viewModels()
  private lateinit var genreMovieAdapter: GenreMovieAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initRecycler()
    getGenreList()
  }

  private fun getGenreList() {
    homeViewModel.getGenreList().observe(viewLifecycleOwner) { stateView ->
      when (stateView) {
        is StateView.Loading -> TODO()
        is StateView.Success -> {
          genreMovieAdapter.submitList(stateView.data)
        }

        is StateView.Error -> {
          stateView.message?.let { Log.e("log_error", it) }
        }
      }
    }
  }

  private fun initRecycler() {
    genreMovieAdapter = GenreMovieAdapter()

    with(binding.recyclerGenre) {
      setHasFixedSize(true)
      adapter = genreMovieAdapter
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}