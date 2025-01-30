package com.rpsouza.movieapp.presenter.main.bottomBar.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ferfalk.simplesearchview.SimpleSearchView
import com.rpsouza.movieapp.MainGraphDirections
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.data.mapper.toMovie
import com.rpsouza.movieapp.databinding.FragmentFavoriteBinding
import com.rpsouza.movieapp.presenter.main.bottomBar.download.adapter.DownloadMovieAdapter
import com.rpsouza.movieapp.presenter.main.bottomBar.home.adapter.MovieAdapter
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.animNavigate
import com.rpsouza.movieapp.utils.applyComponentWindowInsets
import com.rpsouza.movieapp.utils.applyScreenWindowInsets
import com.rpsouza.movieapp.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar, showIconNavigation = false)
        applyScreenWindowInsets(view = view, applyBottom = false)
//        applyComponentWindowInsets(view = binding.recyclerMovies)
        initRecycler()
        getFavoritesList()
        initSearchView()
    }

    private fun getFavoritesList() {
        favoriteViewModel.getFavorites()
            .observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {
                    }

                    is StateView.Success -> {
                        stateView.data?.let { favoriteList ->
                           movieAdapter.submitList(
                                favoriteList.map { it.toMovie() }
                           )
                        }
                    }

                    is StateView.Error -> {
                    }
                }
            }
    }

    private fun initRecycler() {
        movieAdapter = MovieAdapter(
            context = requireContext(),
            itemInflater = R.layout.movie_genre_item,
            movieClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().animNavigate(action)
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
        binding.simpleSearchView.setOnQueryTextListener(object :
            SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty() || newText.isEmpty()) {
//                    favoriteViewModel.searchMovie(newText)
                }
                return true
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.simpleSearchView.setOnSearchViewListener(object :
            SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
            }

            override fun onSearchViewClosed() {
//                downloadViewModel.getMovies()
            }

            override fun onSearchViewShownAnimation() {
            }

            override fun onSearchViewClosedAnimation() {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_view, menu)
        val item = menu.findItem(R.id.action_search)
        binding.simpleSearchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}