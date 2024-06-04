package com.rpsouza.movieapp.presenter.main.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.databinding.FragmentMovieDetailsBinding
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
  private var _binding: FragmentMovieDetailsBinding? = null
  private val binding get() = _binding!!

  private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
  private val args: MovieDetailsFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getMovieDetails()

    initToolbar(toolbar = binding.toolbar, lightIcon = true)
  }

  private fun getMovieDetails() {
    movieDetailsViewModel.getMovieDetails(args.movieId).observe(viewLifecycleOwner) { stateView ->
      when (stateView) {
        is StateView.Loading -> {
//          binding.progressBar.isVisible = true
        }

        is StateView.Success -> {
          stateView.data?.let { configData(it) }
//          binding.progressBar.isVisible = false
        }

        is StateView.Error -> {
//          binding.progressBar.isVisible = false
        }
      }
    }
  }

  private fun configData(movie: Movie) {
    Glide
      .with(requireContext())
      .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
      .into(binding.imageMovie)

    binding.textTitleMovie.text = movie.title
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}