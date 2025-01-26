package com.rpsouza.movieapp.presenter.auth.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentForgotBinding
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.applyScreenWindowInsets
import com.rpsouza.movieapp.utils.hideKeyboard
import com.rpsouza.movieapp.utils.initToolbar
import com.rpsouza.movieapp.utils.isEmailValid
import com.rpsouza.movieapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {
  private var _binding: FragmentForgotBinding? = null
  private val binding get() = _binding!!

  private val forgotViewModel: ForgotViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentForgotBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initToolbar(binding.toolbar)
    initListeners()
  }

  private fun initListeners() {
    binding.btnRecover.setOnClickListener { validateData() }

    Glide
      .with(requireContext())
      .load(R.drawable.loading)
      .into(binding.progressBar)
  }

  private fun validateData() {
    val email = binding.editEmail.text.toString().trim()

    if (email.isEmailValid()) {
      hideKeyboard()
      forgot(email)
    } else {
      showSnackBar(message = R.string.text_email_empty_forgot_fragment)
    }
  }

  private fun forgot(email: String) {
    forgotViewModel.forgot(email).observe(viewLifecycleOwner) { stateView ->
      when (stateView) {
        is StateView.Loading -> {
          binding.progressBar.isVisible = true
        }

        is StateView.Success -> {
          showSnackBar(message = R.string.text_send_email_forgot_fragment)
        }

        is StateView.Error -> {
          binding.progressBar.isVisible = false
          showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}