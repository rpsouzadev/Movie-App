package com.rpsouza.movieapp.presenter.main.movieGenre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ferfalk.simplesearchview.SimpleSearchView
import com.rpsouza.movieapp.MainGraphDirections
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentMovieGenreBinding
import com.rpsouza.movieapp.presenter.main.movieGenre.adapter.LoadStatePagingAdapter
import com.rpsouza.movieapp.presenter.main.movieGenre.adapter.MoviePagingAdapter
import com.rpsouza.movieapp.utils.animNavigate
import com.rpsouza.movieapp.utils.applyScreenWindowInsets
import com.rpsouza.movieapp.utils.hideKeyboard
import com.rpsouza.movieapp.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieGenreFragment : Fragment() {
    private var _binding: FragmentMovieGenreBinding? = null
    private val binding get() = _binding!!

    private val args: MovieGenreFragmentArgs by navArgs()
    private lateinit var moviePagingAdapter: MoviePagingAdapter
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
        applyScreenWindowInsets(view = view, applyBottom = false)
        initRecycler()
        getMovieByGenrePaginationList()
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
                    }

                    is LoadState.NotLoading -> {
                        binding.shimmerContainer.stopShimmer()
                        binding.shimmerContainer.isVisible = false
                        binding.recyclerMovies.isVisible = true
                    }

                    is LoadState.Error -> {
                        binding.shimmerContainer.stopShimmer()
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
        binding.simpleSearchView.setOnQueryTextListener(object :
            SimpleSearchView.OnQueryTextListener {
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

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.simpleSearchView.setOnSearchViewListener(object :
            SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                Log.d("SimpleSearchView", "onSearchViewShown")
            }

            override fun onSearchViewClosed() {
                getMovieByGenrePaginationList()
            }

            override fun onSearchViewShownAnimation() {
                Log.d("SimpleSearchView", "onSearchViewShownAnimation")
            }

            override fun onSearchViewClosedAnimation() {
                Log.d("SimpleSearchView", "onSearchViewClosedAnimation")
            }
        })
    }

    private fun getMovieByGenrePaginationList(forceRequest: Boolean = false) {
        lifecycleScope.launch {
            movieGenreViewModel.getMovieByGenrePaginationList(
                genreId = args.genreId,
                forceRequest = forceRequest
            )
            movieGenreViewModel.movieList.collectLatest { pagingData ->
                moviePagingAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    private fun searchMovies(query: String) {
        lifecycleScope.launch {
            movieGenreViewModel.searchMovies(query).collectLatest { pagingData ->
                moviePagingAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
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