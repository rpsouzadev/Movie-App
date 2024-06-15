package com.rpsouza.movieapp.presenter.main.movieDetails.tabs.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpsouza.movieapp.databinding.FragmentCommentsBinding
import com.rpsouza.movieapp.domain.model.review.AuthorDetails
import com.rpsouza.movieapp.domain.model.review.MovieReview
import com.rpsouza.movieapp.presenter.main.movieDetails.MovieDetailsViewModel
import com.rpsouza.movieapp.presenter.main.movieDetails.adapter.CommentAdapter
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.formatCommentDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment : Fragment() {
    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    private val movieDetailsViewModel: MovieDetailsViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
    }

    private fun initRecycler() {
        commentAdapter = CommentAdapter(context = requireContext())

        with(binding.recyclerComment)
        {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            setHasFixedSize(true)
            adapter = commentAdapter
        }
    }

    private fun initObservers() {
        movieDetailsViewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            if (movieId > 0) {
                getReviews(movieId)
            }
        }
    }

    private fun getReviews(movieId: Int) {
        commentViewModel.getReviews(movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                }

                is StateView.Success -> {
                    commentAdapter.submitList(stateView.data)
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