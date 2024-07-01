package com.rpsouza.movieapp.presenter.main.bottomBar.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rpsouza.movieapp.MainGraphDirections
import com.rpsouza.movieapp.databinding.FragmentSearchBinding
import com.rpsouza.movieapp.presenter.main.movieGenre.adapter.LoadStatePagingAdapter
import com.rpsouza.movieapp.presenter.main.movieGenre.adapter.MoviePagingAdapter
import com.rpsouza.movieapp.utils.animNavigate
import com.rpsouza.movieapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
  private var _binding: FragmentSearchBinding? = null
  private val binding get() = _binding!!

  private val searchViewModel: SearchViewModel by viewModels()
  private lateinit var moviePagingAdapter: MoviePagingAdapter

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
  }

  private fun initRecycler() {
    moviePagingAdapter = MoviePagingAdapter(
      context = requireContext(),
      movieClickListener = { movieId ->
        val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
        findNavController().animNavigate(action)
      }
    )

    lifecycleScope.launch {
      moviePagingAdapter.loadStateFlow.collectLatest { loadState ->
        when (loadState.refresh) {
          is LoadState.Loading -> {
            binding.shimmerContainer.startShimmer()
            binding.recyclerMovies.isVisible = false
            binding.shimmerContainer.isVisible = true
          }

          is LoadState.NotLoading -> {
            binding.shimmerContainer.stopShimmer()
            binding.shimmerContainer.isVisible = false
            binding.recyclerMovies.isVisible = true
          }

          is LoadState.Error -> {
            binding.shimmerContainer.stopShimmer()
            binding.shimmerContainer.isVisible = false
            val error =
              (loadState.refresh as LoadState.Error).error.message ?: "Houve um error"
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
          }
        }
      }
    }


    with(binding.recyclerMovies) {
      setHasFixedSize(true)

      val gridLayoutManager = GridLayoutManager(requireContext(), 2)
      layoutManager = gridLayoutManager

      val footerAdapter = moviePagingAdapter.withLoadStateFooter(
        footer = LoadStatePagingAdapter()
      )

      adapter = footerAdapter

      gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
          return if (position == moviePagingAdapter.itemCount && footerAdapter.itemCount > 0) {
            2
          } else {
            1
          }
        }
      }
    }
  }

  private fun initSearchView() {
    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        hideKeyboard()
        if (query.isNotEmpty()) {
          searchMovies(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
    })
  }

  private fun searchMovies(query: String) {
    lifecycleScope.launch {
      searchViewModel.searchMovies(query).collectLatest { pagingData ->
        moviePagingAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
      }
    }
  }

  private fun emptyState(emptyList: Boolean) {
    binding.recyclerMovies.isVisible = !emptyList
    binding.layoutEmpty.isVisible = emptyList
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}