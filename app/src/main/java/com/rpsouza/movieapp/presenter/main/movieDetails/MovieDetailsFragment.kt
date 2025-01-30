package com.rpsouza.movieapp.presenter.main.movieDetails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.data.mapper.toFavoriteMovie
import com.rpsouza.movieapp.databinding.DialogDownloadBinding
import com.rpsouza.movieapp.databinding.FragmentMovieDetailsBinding
import com.rpsouza.movieapp.domain.model.favorite.FavoriteMovie
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.presenter.main.movieDetails.adapter.CastAdapter
import com.rpsouza.movieapp.presenter.main.movieDetails.adapter.ViewPagerAdapter
import com.rpsouza.movieapp.presenter.main.movieDetails.tabs.comments.CommentsFragment
import com.rpsouza.movieapp.presenter.main.movieDetails.tabs.similar.SimilarFragment
import com.rpsouza.movieapp.presenter.main.movieDetails.tabs.trailers.TrailersFragment
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.FormatDate
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.ViewPager2ViewHeightAnimator
import com.rpsouza.movieapp.utils.calculateFileSize
import com.rpsouza.movieapp.utils.initToolbar
import com.rpsouza.movieapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val movieDetailsViewModel: MovieDetailsViewModel by activityViewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var castAdapter: CastAdapter
    private lateinit var dialogDownload: AlertDialog

    private lateinit var movie: Movie
    private val favoriteList: MutableList<FavoriteMovie> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar, lightIcon = true)
        initRecyclerCast()
        getMovieDetails()
        configTabLayout()
        initListeners()
    }

    private fun initListeners() {
        binding.btnDownload.setOnClickListener { showDialogDownload() }
        binding.imageBookmark.setOnClickListener {
            if (favoriteList.contains(movie.toFavoriteMovie())) {
                favoriteList.remove(movie.toFavoriteMovie())
                binding.imageBookmark.setImageResource(R.drawable.ic_bookmark_line)
            } else {
                favoriteList.add(movie.toFavoriteMovie())
                binding.imageBookmark.setImageResource(R.drawable.ic_bookmark_fill)
            }

            saveFavorites()
        }
    }

    private fun configTabLayout() {
        movieDetailsViewModel.setMovieId(movieId = args.movieId)

        val viewPagerAdapter = ViewPagerAdapter(requireActivity())
        val viewPager = ViewPager2ViewHeightAnimator()

        viewPager.viewPager2 = binding.viewPager
        viewPager.viewPager2?.adapter = viewPagerAdapter

        viewPagerAdapter.addFragment(
            fragment = TrailersFragment(),
            title = R.string.title_trailers_tab_layout
        )

        viewPagerAdapter.addFragment(
            fragment = SimilarFragment(),
            title = R.string.title_similar_tab_layout
        )

        viewPagerAdapter.addFragment(
            fragment = CommentsFragment(),
            title = R.string.title_comments_tab_layout
        )

        binding.viewPager.offscreenPageLimit = viewPagerAdapter.itemCount

        viewPager.viewPager2?.let { viewPager2 ->
            TabLayoutMediator(
                binding.tabLayout,
                viewPager2,
            ) { tab, position ->
                tab.text = getString(viewPagerAdapter.getTitle(position))
            }.attach()
        }
    }

    private fun getMovieDetails() {
        movieDetailsViewModel.getMovieDetails(args.movieId)
            .observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {
                    }

                    is StateView.Success -> {
                        stateView.data?.let {
                            this.movie = it
                            configData()
                        }
                    }

                    is StateView.Error -> {
                    }
                }
            }
    }

    private fun insertMovieLocal() {
        if (view != null) {
            movieDetailsViewModel.insertMovieLocal(movie)
                .observe(viewLifecycleOwner) { stateView ->
                    when (stateView) {
                        is StateView.Loading -> {
                        }

                        is StateView.Success -> {
                        }

                        is StateView.Error -> {
                        }
                    }
                }
        }
    }

    private fun saveFavorites() {
        movieDetailsViewModel.saveFavorites(favoriteList).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {}

                is StateView.Success -> {

                }

                is StateView.Error -> {
                    showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
                }
            }
        }
    }

    private fun getCredits() {
        movieDetailsViewModel.getCredits(args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
//          binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    stateView.data?.let { castAdapter.submitList(it) }
//          binding.progressBar.isVisible = false
                }

                is StateView.Error -> {
//          binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun configData() {
        Glide
            .with(requireContext())
            .load("${movie.backdropPath}")
            .into(binding.imageMovie)

        binding.textTitleMovie.text = movie.title

        binding.textVoteAverage.text =
            String.format(Locale.getDefault(), "%.1f", movie.voteAverage)
        binding.textReleaseDate.text = FormatDate.yearFormat(movie.releaseDate ?: "")
        binding.textProductionCountry.text = movie.productionCountries?.get(0)?.name
        binding.textOriginalTitle.text = movie.originalTitle

        val genres = movie.genres?.map { it.name }?.joinToString()
        binding.textGenres.text = getString(R.string.text_all_genres_movie_details_fragment, genres)

        binding.textDescription.text = movie.overview

        getCredits()
    }

    private fun initRecyclerCast() {
        castAdapter = CastAdapter(requireContext())

        with(binding.rvCast) {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            adapter = castAdapter
        }
    }

    private fun showDialogDownload() {
        val dialogBinding = DialogDownloadBinding.inflate(LayoutInflater.from(requireContext()))
        var progress = 0
        var downloaded = 0.0
        val movieDuration = movie.runtime?.toDouble() ?: 0.0

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (progress < 100) {
                    downloaded += (movieDuration / 100.0)
                    dialogBinding.textDownloading.text = getString(
                        R.string.text_downloaded_size_dialog_downloading,
                        downloaded.calculateFileSize(),
                        movieDuration.calculateFileSize()
                    )

                    progress++
                    dialogBinding.progressIndicator.progress = progress
                    dialogBinding.textProgress.text = getString(
                        R.string.text_download_progress_dialog_downloading,
                        progress
                    )

                    handler.postDelayed(this, 100)
                } else {
                    if (isAdded) {
                        insertMovieLocal()
                        dialogDownload.dismiss()
                    }
                }
            }
        }

        handler.post(runnable)

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
        builder.setView(dialogBinding.root)

        dialogBinding.buttonHidden.setOnClickListener { dialogDownload.dismiss() }
        dialogBinding.imageButtonCancel.setOnClickListener { dialogDownload.dismiss() }

        dialogDownload = builder.create()
        dialogDownload.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}