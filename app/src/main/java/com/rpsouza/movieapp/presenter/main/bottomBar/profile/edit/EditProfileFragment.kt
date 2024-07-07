package com.rpsouza.movieapp.presenter.main.bottomBar.profile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentEditProfileBinding
import com.rpsouza.movieapp.utils.hideKeyboard
import com.rpsouza.movieapp.utils.initToolbar
import com.rpsouza.movieapp.utils.showSnackBar

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

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
        initListeners()
    }

    private fun initListeners() {
        binding.buttonUpdate.setOnClickListener { validateData() }
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

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}