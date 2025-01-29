package com.rpsouza.movieapp.presenter.main.bottomBar.profile

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.BottomSheetLogoutBinding
import com.rpsouza.movieapp.databinding.FragmentProfileBinding
import com.rpsouza.movieapp.domain.model.menuProfile.MenuProfile
import com.rpsouza.movieapp.domain.model.menuProfile.MenuProfileType
import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.presenter.auth.activity.AuthActivity
import com.rpsouza.movieapp.presenter.auth.activity.AuthActivity.Companion.AUTHENTICATION_PARAMETER
import com.rpsouza.movieapp.presenter.auth.enums.AuthenticationDestinations
import com.rpsouza.movieapp.presenter.main.bottomBar.profile.adapter.ProfileMenuAdapter
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.animNavigate
import com.rpsouza.movieapp.utils.applyScreenWindowInsets
import com.rpsouza.movieapp.utils.initToolbar
import com.rpsouza.movieapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var profileMenuAdapter: ProfileMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar, showIconNavigation = false)
        applyScreenWindowInsets(view = view, applyBottom = false)
        getUser()
        initRecyclerView()
    }

    private fun getUser() {
        profileViewModel.getUser().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
//                    showLoading(true)
                }

                is StateView.Success -> {
//                    showLoading(false)
                    stateView.data?.let { configData(it) }
                }

                is StateView.Error -> {
//                    showLoading(false)
                    showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
                }
            }
        }
    }

    private fun initRecyclerView() {
        profileMenuAdapter = ProfileMenuAdapter(
            menuItems = MenuProfile.menuItems,
            context = requireContext(),
            onClick = { menuProfileType ->
                when (menuProfileType) {
                    MenuProfileType.PROFILE -> {
                        val action =
                            ProfileFragmentDirections.actionMenuProfileToEditProfileFragment()
                        findNavController().animNavigate(action)
                    }

                    MenuProfileType.NOTIFICATION -> {}
                    MenuProfileType.DOWNLOAD -> {
                        val bottomNavigation =
                            activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
                        bottomNavigation?.selectedItemId = R.id.menu_download
                    }

                    MenuProfileType.SECURITY -> {}
                    MenuProfileType.LANGUAGE -> {}
                    MenuProfileType.DARK_MODE -> {}
                    MenuProfileType.HELPER -> {}
                    MenuProfileType.PRIVACY_POLICY -> {}
                    MenuProfileType.LOGOUT -> {
                        showBottomSheetLogout()
                    }
                }
            })

        with(binding.recyclerProfileItems) {
            adapter = profileMenuAdapter
            setHasFixedSize(true)
        }
    }

    private fun configData(user: User) {
        binding.textUsername.text = getString(
            R.string.text_username_profile_fragment,
            user.firstName,
            user.lastName
        )
        binding.textEmail.text = user.email

        binding.tvPhotoEmpty.isVisible = user.photoUrl.isNullOrEmpty()
        binding.tvPhotoEmpty.text = getString(
            R.string.text_photo_empty_profile_fragment,
            user.firstName?.first(),
            user.lastName?.first()
        )

        user.photoUrl?.let { url ->
            binding.tvPhotoEmpty.isVisible = false
            binding.imageProfile.isVisible = true
            Glide
                .with(requireContext())
                .load(url)
                .into(binding.imageProfile)
        }
    }

    private fun showBottomSheetLogout() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)

        val bottomSheetBinding = BottomSheetLogoutBinding.inflate(
            layoutInflater,
            null,
            false
        )

        bottomSheetBinding.buttonCancel.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetBinding.buttonConfirm.setOnClickListener {
            bottomSheetDialog.dismiss()
            logout()
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        activity?.finish()
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.putExtra(AUTHENTICATION_PARAMETER, AuthenticationDestinations.LOGIN_SCREEN)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}