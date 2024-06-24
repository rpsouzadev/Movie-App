package com.rpsouza.movieapp.presenter.main.bottomBar.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rpsouza.movieapp.MainGraphDirections
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.BottomSheetDeleteMovieBinding
import com.rpsouza.movieapp.databinding.FragmentDownloadBinding
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.presenter.main.bottomBar.download.adapter.DownloadMovieAdapter
import com.rpsouza.movieapp.utils.calculateFileSize
import com.rpsouza.movieapp.utils.calculateMovieTime
import com.rpsouza.movieapp.utils.hideKeyboard
import com.rpsouza.movieapp.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadFragment : Fragment() {
    private var _binding: FragmentDownloadBinding? = null
    private val binding get() = _binding!!

    private lateinit var downloadMovieAdapter: DownloadMovieAdapter
    private val downloadViewModel: DownloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar, showIconNavigation = false)
        initRecycler()
        initObservers()
        getMoviesData()
        initListeners()
    }

    private fun getMoviesData() {
        downloadViewModel.getMovies()
    }

    private fun initObservers() {
        downloadViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            downloadMovieAdapter.submitList(movieList)
            emptyState(emptyList = movieList.isEmpty())
        }

        downloadViewModel.movieSearchList.observe(viewLifecycleOwner) { movieList ->
            downloadMovieAdapter.submitList(movieList)
            emptyState(emptyList = movieList.isEmpty())
        }
    }

    private fun initListeners() {
        initSearchView()
        onBackPressed()
    }

    private fun initRecycler() {
        downloadMovieAdapter = DownloadMovieAdapter(
            context = requireContext(),
            detailsClickListener = { movieId ->
                val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigate(action)
            },
            deleteClickListener = { movie ->
                showBottomSheetDeleteMovie(movie)
            }
        )

        with(binding.recyclerDownload) {
            setHasFixedSize(true)
            adapter = downloadMovieAdapter
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
                    downloadViewModel.searchMovie(newText)
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
                downloadViewModel.getMovies()
            }

            override fun onSearchViewShownAnimation() {
            }

            override fun onSearchViewClosedAnimation() {
            }
        })
    }

    private fun showBottomSheetDeleteMovie(movie: Movie) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)

        val bottomSheetBinding = BottomSheetDeleteMovieBinding.inflate(
            layoutInflater,
            null,
            false
        )


        Glide
            .with(requireContext())
            .load(movie.posterPath)
            .into(bottomSheetBinding.ivMovie)

        bottomSheetBinding.textMovie.text = movie.title
        bottomSheetBinding.textDuration.text = movie.runtime?.calculateMovieTime()
        bottomSheetBinding.textSize.text = movie.runtime?.toDouble()?.calculateFileSize()

        bottomSheetBinding.buttonCancel.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetBinding.buttonConfirm.setOnClickListener {
            downloadViewModel.deleteMovie(movie.id)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun emptyState(emptyList: Boolean) {
        binding.recyclerDownload.isVisible = !emptyList
        binding.layoutEmpty.isVisible = emptyList
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.simpleSearchView.isVisible) {
                        binding.simpleSearchView.closeSearch()
                    } else {
                        findNavController().popBackStack()
                    }
                }
            }
        )
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