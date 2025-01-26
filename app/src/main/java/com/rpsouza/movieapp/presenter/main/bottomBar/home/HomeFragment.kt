package com.rpsouza.movieapp.presenter.main.bottomBar.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rpsouza.movieapp.MainGraphDirections
import com.rpsouza.movieapp.databinding.FragmentHomeBinding
import com.rpsouza.movieapp.presenter.main.bottomBar.home.adapter.GenreMovieAdapter
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.animNavigate
import com.rpsouza.movieapp.utils.applyScreenWindowInsets
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
        applyScreenWindowInsets(view = view, applyBottom = false)
        initRecycler()
        initObservers()
    }

    private fun initObservers() {
        homeViewModel.homeState.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerGenre.isVisible = false
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerGenre.isVisible = true
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerGenre.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        homeViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            genreMovieAdapter.submitList(movieList)
        }
    }

    private fun initRecycler() {
        genreMovieAdapter = GenreMovieAdapter(
            seeAllList = { genreId, genreName ->
                val action =
                    HomeFragmentDirections.actionMenuHomeToMovieGenreFragment(genreId, genreName)
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