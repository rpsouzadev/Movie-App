package com.rpsouza.movieapp.presenter.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentLoginBinding
import com.rpsouza.movieapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
  private var _binding: FragmentLoginBinding? = null
  private val binding get() = _binding!!

  private val loginViewModel: LoginViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentLoginBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initListeners()
  }

  private fun initListeners() {
    binding.btnLogin.setOnClickListener { validateData() }

    Glide
      .with(requireContext())
      .load(R.drawable.loading)
      .into(binding.progressBar)
  }

  private fun validateData() {
    val email = binding.editEmail.text.toString().trim()
    val password = binding.editPassword.text.toString().trim()

    if (email.isNotEmpty()) {
      if (password.isNotEmpty()) {
        register(email, password)
      } else {

      }
    } else {

    }
  }

  private fun register(email: String, password: String) {
    loginViewModel.login(email, password).observe(viewLifecycleOwner) { stateView ->
      when (stateView) {
        is StateView.Loading -> {
          binding.progressBar.isVisible = true
        }

        is StateView.Success -> {
          Toast.makeText(requireContext(), "Ligin com Sucesso!", Toast.LENGTH_LONG).show()
        }

        is StateView.Error -> {
          binding.progressBar.isVisible = false
          Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_LONG).show()
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}