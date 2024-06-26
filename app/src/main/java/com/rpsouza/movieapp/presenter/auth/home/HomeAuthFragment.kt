package com.rpsouza.movieapp.presenter.auth.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentHomeAuthBinding
import com.rpsouza.movieapp.utils.animNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAuthFragment : Fragment() {
  private var _binding: FragmentHomeAuthBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentHomeAuthBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initListeners()
  }

  private fun initListeners() {
    binding.btnLoginWithPassword.setOnClickListener {
      val action = R.id.action_homeAuthFragment_to_loginFragment
      findNavController().animNavigate(action)
    }

    binding.textBtnRegister.setOnClickListener {
      val action = R.id.action_homeAuthFragment_to_registerFragment
      findNavController().animNavigate(action)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}