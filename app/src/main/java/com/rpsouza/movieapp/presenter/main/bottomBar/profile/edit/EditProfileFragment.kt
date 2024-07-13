package com.rpsouza.movieapp.presenter.main.bottomBar.profile.edit

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
import com.rpsouza.movieapp.databinding.FragmentEditProfileBinding
import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.presenter.main.activity.MainActivity
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.hideKeyboard
import com.rpsouza.movieapp.utils.initToolbar
import com.rpsouza.movieapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val editProfileViewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar)
        getUser()
        initListeners()
    }

    private fun initListeners() {
        binding.buttonUpdate.setOnClickListener { validateData() }

        Glide
            .with(requireContext())
            .load(R.drawable.loading)
            .into(binding.progressBar)
    }

    private fun validateData() {
        val firstName = binding.editFirstName.text.toString()
        val lastName = binding.editLastName.text.toString()
        val phone = binding.editPhone.text.toString()
        val gender = binding.editGender.text.toString()
        val country = binding.editCountry.text.toString()

        hideKeyboard()

        if (firstName.isEmpty()) {
            showSnackBar(message = R.string.text_first_name_empty_edit_profile_fragment)
            return
        }

        if (lastName.isEmpty()) {
            showSnackBar(message = R.string.text_last_name_empty_edit_profile_fragment)
            return
        }

        if (phone.isEmpty()) {
            showSnackBar(message = R.string.text_phone_empty_edit_profile_fragment)
            return
        }

        if (gender.isEmpty()) {
            showSnackBar(message = R.string.text_gender_empty_edit_profile_fragment)
            return
        }

        if (country.isEmpty()) {
            showSnackBar(message = R.string.text_country_empty_edit_profile_fragment)
            return
        }

        val user = User(
            id = FirebaseHelper.getUserId(),
            firstName = firstName,
            lastName = lastName,
            email = FirebaseHelper.getAuth().currentUser?.email,
            phone = phone,
            gender = gender,
            country = country
        )

        updateUser(user)
    }

    private fun updateUser(user: User) {
        editProfileViewModel.updateUser(user).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.buttonUpdate.isEnabled = false
                }

                is StateView.Success -> {
                    showSnackBar(message = R.string.text_update_success_edit_profile_fragment)
                    binding.progressBar.isVisible = false
                    binding.buttonUpdate.isEnabled = true
                }

                is StateView.Error -> {
                    binding.buttonUpdate.isEnabled = true
                    binding.progressBar.isVisible = false
                    showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
                }
            }
        }
    }

    private fun getUser() {
        editProfileViewModel.getUser().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
//                    binding.progressBar.isVisible = true
//                    binding.buttonUpdate.isEnabled = false
                }

                is StateView.Success -> {
                    stateView.data?.let { configData(it) }
//                    binding.progressBar.isVisible = false
//                    binding.buttonUpdate.isEnabled = true
                }

                is StateView.Error -> {
//                    binding.buttonUpdate.isEnabled = true
//                    binding.progressBar.isVisible = false
                    showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
                }
            }
        }
    }

    private fun configData(user: User) {
        binding.editFirstName.setText(user.firstName)
        binding.editLastName.setText(user.lastName)
        binding.editEmail.setText(user.email)
        binding.editPhone.setText(user.phone)
        binding.editGender.setText(user.gender)
        binding.editCountry.setText(user.country)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}