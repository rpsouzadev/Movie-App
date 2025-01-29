package com.rpsouza.movieapp.presenter.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentLoginBinding
import com.rpsouza.movieapp.presenter.main.activity.MainActivity
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.animNavigate
import com.rpsouza.movieapp.utils.applyScreenWindowInsets
import com.rpsouza.movieapp.utils.hideKeyboard
import com.rpsouza.movieapp.utils.initToolbar
import com.rpsouza.movieapp.utils.isEmailValid
import com.rpsouza.movieapp.utils.showSnackBar
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
    initToolbar(binding.toolbar)
    initListeners()
  }

  private fun initListeners() {
    binding.textBtnForgot.setOnClickListener {
      val action = R.id.action_loginFragment_to_forgotFragment
      findNavController().animNavigate(action)
    }

    binding.btnLogin.setOnClickListener { validateData() }

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
        login(email, password)
      } else {
        showSnackBar(message = R.string.text_password_empty_login_fragment)
      }
    } else {
      showSnackBar(message = R.string.text_email_empty_login_fragment)
    }
  }

  private fun login(email: String, password: String) {
    loginViewModel.login(email, password).observe(viewLifecycleOwner) { stateView ->
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