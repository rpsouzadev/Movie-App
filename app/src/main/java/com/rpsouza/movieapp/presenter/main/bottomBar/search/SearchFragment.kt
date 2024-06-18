package com.rpsouza.movieapp.presenter.main.bottomBar.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rpsouza.movieapp.MainGraphDirections
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentSearchBinding
import com.rpsouza.movieapp.presenter.main.bottomBar.home.adapter.MovieAdapter
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
  private var _binding: FragmentSearchBinding? = null
  private val binding get() = _binding!!

  private val searchViewModel: SearchViewModel by viewModels()
  private lateinit var movieAdapter: MovieAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentSearchBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecycler()
    initSearchView()
    initObservers()
  }

  private fun initObservers() {
    initStateObserver()
    searchObserver()
  }

  private fun initRecycler() {
    movieAdapter = MovieAdapter(
      context = requireContext(),
      itemInflater = R.layout.movie_genre_item,
      movieClickListener = { movieId ->
        val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
        findNavController().navigate(action)
      }
    )

    with(binding.recyclerMovies)
    {
      layoutManager = GridLayoutManager(requireContext(), 2)
      setHasFixedSize(true)
      adapter = movieAdapter
    }
  }

  private fun initSearchView() {
    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        hideKeyboard()
        if (query.isNotEmpty()) {
          searchViewModel.searchMovies(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
    })
  }

  private fun initStateObserver() {
    searchViewModel.searchState.observe(viewLifecycleOwner) { stateView ->
      when (stateView) {
        is StateView.Loading -> {
          binding.recyclerMovies.isVisible = false
          binding.progressBar.isVisible = true
        }

        is StateView.Success -> {
          binding.progressBar.isVisible = false
          binding.recyclerMovies.isVisible = true
        }

        is StateView.Error -> {
          binding.progressBar.isVisible = false
        }
      }
    }
  }

  private fun searchObserver() {
    searchViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
      movieAdapter.submitList(movieList)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}