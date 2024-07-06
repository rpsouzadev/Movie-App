package com.rpsouza.movieapp.presenter.main.bottomBar.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.FragmentProfileBinding
import com.rpsouza.movieapp.domain.model.menuProfile.MenuProfile
import com.rpsouza.movieapp.domain.model.menuProfile.MenuProfileType
import com.rpsouza.movieapp.presenter.main.bottomBar.profile.adapter.ProfileMenuAdapter
import com.rpsouza.movieapp.utils.initToolbar

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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
        initRecyclerView()
        configData()
    }

    private fun initRecyclerView() {
        profileMenuAdapter = ProfileMenuAdapter(
            menuItems = MenuProfile.menuItems,
            context = requireContext(),
            onClick = { menuProfileType ->
                when (menuProfileType) {
                    MenuProfileType.PROFILE -> {}
                    MenuProfileType.NOTIFICATION -> {}
                    MenuProfileType.DOWNLOAD -> {}
                    MenuProfileType.SECURITY -> {}
                    MenuProfileType.LANGUAGE -> {}
                    MenuProfileType.DARK_MODE -> {}
                    MenuProfileType.HELPER -> {}
                    MenuProfileType.PRIVACY_POLICY -> {}
                    MenuProfileType.LOGOUT -> {}
                }
            })

        with(binding.recyclerProfileItems) {
            adapter = profileMenuAdapter
            setHasFixedSize(true)
        }
    }

    private fun configData() {
        binding.imageProfile.setImageResource(R.drawable.person_placeholder)
        binding.textUsername.text = "Rafael Souza"
        binding.textEmail.text = "user1@email.com"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}