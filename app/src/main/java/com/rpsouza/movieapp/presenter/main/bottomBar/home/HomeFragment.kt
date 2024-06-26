package com.rpsouza.movieapp.presenter.main.bottomBar.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rpsouza.movieapp.MainGraphDirections
import com.rpsouza.movieapp.databinding.FragmentHomeBinding
import com.rpsouza.movieapp.presenter.main.bottomBar.home.adapter.GenreMovieAdapter
import com.rpsouza.movieapp.presenter.model.GenrePresentation
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.animNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        is StateView.Loading -> {}
        is StateView.Success -> {
          val genreList = stateView.data ?: emptyList()
          getMovieByGenreList(genreList)
        }

        is StateView.Error -> {

        }
      }
    }
  }

  private fun getMovieByGenreList(genreList: List<GenrePresentation>) {
    val genreMutableList = genreList.toMutableList()

    genreMutableList.forEachIndexed { index, genre ->
      homeViewModel.getMovieByGenreList(genre.id).observe(viewLifecycleOwner) { stateView ->
        when (stateView) {
          is StateView.Loading -> {}
          is StateView.Success -> {
            genreMutableList[index] = genre.copy(movies = stateView.data?.take(7))
            lifecycleScope.launch {
              delay(1000)
              genreMovieAdapter.submitList(genreMutableList)
            }
          }

          is StateView.Error -> {

          }
        }
      }
    }
  }

  private fun initRecycler() {
    genreMovieAdapter = GenreMovieAdapter(
      seeAllList = { genreId, genreName ->
        val action = HomeFragmentDirections.actionMenuHomeToMovieGenreFragment(genreId, genreName)
        findNavController().animNavigate(action)
      },
      
      movieClickListener = { movieId ->
        val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
        findNavController().animNavigate(action)
      }
    )

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