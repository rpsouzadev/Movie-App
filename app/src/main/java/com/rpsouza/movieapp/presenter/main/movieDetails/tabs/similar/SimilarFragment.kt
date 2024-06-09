package com.rpsouza.movieapp.presenter.main.movieDetails.tabs.similar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpsouza.movieapp.MainGraphDirections
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentSimilarBinding
import com.rpsouza.movieapp.presenter.main.bottomBar.home.adapter.MovieAdapter
import com.rpsouza.movieapp.presenter.main.movieDetails.MovieDetailsViewModel
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimilarFragment : Fragment() {
    private var _binding: FragmentSimilarBinding? = null
    private val binding get() = _binding!!

    private val movieDetailsViewModel: MovieDetailsViewModel by activityViewModels()
    private val similarViewModel: SimilarViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimilarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
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

    private fun initObservers() {
        movieDetailsViewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            if (movieId > 0) {
                getSimilar(movieId)
            }
        }
    }

    private fun getSimilar(movieId: Int) {
        similarViewModel.getSimilar(movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                }

                is StateView.Success -> {
                    movieAdapter.submitList(stateView.data)
                }

                is StateView.Error -> {
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}