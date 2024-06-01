package com.rpsouza.movieapp.presenter.main.movieGenre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ferfalk.simplesearchview.SimpleSearchView
import com.rpsouza.movieapp.R
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

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
    binding.toolbar.title = args.genreName
    initRecycler()
    getMovieByGenreList()
    initSearchView()
  }

  private fun initRecycler() {
    movieAdapter = MovieAdapter(
      context = requireContext(),
      itemInflater = R.layout.movie_genre_item
    )

    with(binding.recyclerMovies) {
      layoutManager = GridLayoutManager(requireContext(), 2)
      setHasFixedSize(true)
      adapter = movieAdapter
    }
  }

  private fun initSearchView() {
    binding.simpleSearchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        Log.d("SimpleSearchView", "Submit:$query")
        return false
      }

      override fun onQueryTextChange(newText: String): Boolean {
        Log.d("SimpleSearchView", "Text changed:$newText")
        return false
      }

      override fun onQueryTextCleared(): Boolean {
        Log.d("SimpleSearchView", "Text cleared")
        return false
      }
    })
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

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_search_view, menu)
    val item = menu.findItem(R.id.action_search)
    binding.simpleSearchView.setMenuItem(item)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}