package com.rpsouza.movieapp.presenter.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentRegisterBinding
import com.rpsouza.movieapp.presenter.main.activity.MainActivity
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.applyScreenWindowInsets
import com.rpsouza.movieapp.utils.hideKeyboard
import com.rpsouza.movieapp.utils.initToolbar
import com.rpsouza.movieapp.utils.isEmailValid
import com.rpsouza.movieapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
  private var _binding: FragmentRegisterBinding? = null
  private val binding get() = _binding!!

  private val registerViewModel: RegisterViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentRegisterBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initToolbar(binding.toolbar)
    applyScreenWindowInsets(view = binding.toolbar, applyBottom = false)
    initListeners()
  }

  private fun initListeners() {
    binding.btnRegister.setOnClickListener { validateData() }

    Glide
      .with(requireContext())
      .load(R.drawable.loading)
      .into(binding.progressBar)
  }

  private fun validateData() {
    val email = binding.editEmail.text.toString().trim()
    val password = binding.editPassword.text.toString().trim()

    if (email.isEmailValid()) {
      if (password.isNotEmpty()) {
        hideKeyboard()
        register(email, password)
      } else {
        showSnackBar(message = R.string.text_password_empty_register_fragment)
      }
    } else {
      showSnackBar(message = R.string.text_email_empty_register_fragment)
    }
  }

  private fun register(email: String, password: String) {
    registerViewModel.register(email, password).observe(viewLifecycleOwner) { stateView ->
      when (stateView) {
        is StateView.Loading -> {
          binding.progressBar.isVisible = true
        }

        is StateView.Success -> {
          startActivity(Intent(requireContext(), MainActivity::class.java))
          requireActivity().finish()
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