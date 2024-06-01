package com.rpsouza.movieapp.presenter.main.movieGenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.rpsouza.movieapp.databinding.FragmentMovieGenreBinding
import com.rpsouza.movieapp.presenter.main.bottomBar.home.adapter.MovieAdapter
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieGenreFragment : Fragment() {
  private var _binding: FragmentMovieGenreBinding? = null
  private val binding get() = _binding!!

  private val args: MovieGenreFragmentArgs by navArgs()
  private lateinit var movieAdapter: MovieAdapter
  private val movieGenreViewModel: MovieGenreViewModel by viewModels()

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
    initRecycler()
    getMovieByGenreList()
  }

  private fun initRecycler() {
    movieAdapter = MovieAdapter(requireContext())

    with(binding.recyclerMovies) {
      layoutManager = GridLayoutManager(requireContext(), 2)
      setHasFixedSize(true)
      adapter = movieAdapter
    }
  }

  private fun getMovieByGenreList() {
    movieGenreViewModel.getMovieByGenreList(args.genreId).observe(viewLifecycleOwner) { stateView ->
      when (stateView) {
        is StateView.Loading -> {
          binding.progressBar.isVisible = true
        }
        is StateView.Success -> {
          binding.progressBar.isVisible = false
          movieAdapter.submitList(stateView.data)
        }

        is StateView.Error -> {
          binding.progressBar.isVisible = false
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}