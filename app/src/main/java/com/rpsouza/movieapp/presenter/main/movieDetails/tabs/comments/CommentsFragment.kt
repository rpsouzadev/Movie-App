package com.rpsouza.movieapp.presenter.main.movieDetails.tabs.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpsouza.movieapp.databinding.FragmentCommentsBinding
import com.rpsouza.movieapp.domain.model.review.AuthorDetails
import com.rpsouza.movieapp.domain.model.review.MovieReview
import com.rpsouza.movieapp.presenter.main.movieDetails.adapter.CommentAdapter
import com.rpsouza.movieapp.utils.formatCommentDate

class CommentsFragment : Fragment() {
  private var _binding: FragmentCommentsBinding? = null
  private val binding get() = _binding!!

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
    commentAdapter.submitList(fakeList())
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

  private fun fakeList(): List<MovieReview> {
    return listOf(
      MovieReview(
        author = "thealanfrench",
        authorDetails = AuthorDetails(
          name = "",
          username = "thealanfrench",
          avatarPath = "https://image.tmdb.org/t/p/w500/23f2cd16e6fafdf013b30ccc22e2e4c8.jpg",
          rating = 5
        ),
        content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
        createdAt = "2023-03-15T05:13:49.138Z",
        id = "6411540dfe6c1800bb659ebd",
        updatedAt = "2023-03-15T05:13:49.138Z",
        url = "https://www.themoviedb.org/review/6411540dfe6c1800bb659ebd"
      )
    )
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}