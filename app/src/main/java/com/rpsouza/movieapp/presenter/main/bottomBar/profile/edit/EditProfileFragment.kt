package com.rpsouza.movieapp.presenter.main.bottomBar.profile.edit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rpsouza.movieapp.R
import com.rpsouza.movieapp.databinding.BottomSheetPermissionDeniedBinding
import com.rpsouza.movieapp.databinding.BottomSheetSelectImageBinding
import com.rpsouza.movieapp.databinding.FragmentEditProfileBinding
import com.rpsouza.movieapp.domain.model.user.User
import com.rpsouza.movieapp.utils.FirebaseHelper
import com.rpsouza.movieapp.utils.StateView
import com.rpsouza.movieapp.utils.applyScreenWindowInsets
import com.rpsouza.movieapp.utils.hideKeyboard
import com.rpsouza.movieapp.utils.initToolbar
import com.rpsouza.movieapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val editProfileViewModel: EditProfileViewModel by viewModels()

    private val GALLERY_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    private val CAMERA_PERMISSION = Manifest.permission.CAMERA


    private var currentPhotoUri: Uri? = null
    private var user: User? = null

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
        applyScreenWindowInsets(view = view)
        getUser()
        iniObservers()
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            imageEdit.setOnClickListener {
                openBottomSheetSelectImage()
            }

            buttonUpdate.setOnClickListener {
                editProfileViewModel.validateData(
                    firstName = editFirstName.text.toString(),
                    lastName = editLastName.text.toString(),
                    phone = editPhone.text.toString(),
                    gender = editGender.text.toString(),
                    country = editCountry.text.toString()
                )
                hideKeyboard()
            }

            Glide
                .with(requireContext())
                .load(R.drawable.loading)
                .into(progressBar)
        }

    }

    private fun iniObservers() {
        editProfileViewModel.validateData.observe(viewLifecycleOwner) { (validated, message) ->
            if (!validated) {
                message?.let { showSnackBar(message = it) }
            } else {
                if (currentPhotoUri != null) {
                    saveUserImage()
                } else {
                    updateUser()
                }
            }
        }
    }

    private fun updateUser(photoUrl: String? = null) {
        val user = User(
            id = FirebaseHelper.getUserId(),
            firstName = binding.editFirstName.text.toString(),
            lastName = binding.editLastName.text.toString(),
            email = binding.editEmail.text.toString(),
            photoUrl = photoUrl ?: user?.photoUrl,
            phone = binding.editPhone.text.toString(),
            gender = binding.editGender.text.toString(),
            country = binding.editCountry.text.toString()
        )

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
                    showLoading(true)
                }

                is StateView.Success -> {
                    showLoading(false)
                    user = stateView.data
                    configData()
                }

                is StateView.Error -> {
                    showLoading(false)
                    showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
                }
            }
        }
    }

    private fun saveUserImage() {
        currentPhotoUri?.let { uri ->
            editProfileViewModel.saveUserImage(uri).observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {}

                    is StateView.Success -> {
                        updateUser(photoUrl = stateView.data)
                    }

                    is StateView.Error -> {
                        showLoading(false)
                        showSnackBar(message = FirebaseHelper.validError(stateView.message ?: ""))
                    }
                }
            }
        }
    }

    private fun configData() {
        binding.editFirstName.setText(user?.firstName)
        binding.editLastName.setText(user?.lastName)
        binding.editEmail.setText(user?.email)
        binding.editPhone.setText(user?.phone)
        binding.editGender.setText(user?.gender)
        binding.editCountry.setText(user?.country)

        binding.tvPhotoEmpty.isVisible = user?.photoUrl.isNullOrEmpty()
        binding.tvPhotoEmpty.text = getString(
            R.string.text_photo_empty_edit_profile_fragment,
            user?.firstName?.first(),
            user?.lastName?.first()
        )

        user?.photoUrl?.let { url ->
            binding.tvPhotoEmpty.isVisible = false
            binding.imageProfile.isVisible = true
            Glide
                .with(requireContext())
                .load(url)
                .into(binding.imageProfile)
        }
    }

    private fun openBottomSheetSelectImage() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetSelectImageBinding.inflate(
            layoutInflater,
            null,
            false
        )

        bottomSheetBinding.btnCamera.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkCameraPermission()
        }

        bottomSheetBinding.btnGallery.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkGalleryPermission()
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun showBottomSheetPermissionDenied() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val bottomSheetBinding = BottomSheetPermissionDeniedBinding.inflate(
            layoutInflater,
            null,
            false
        )

        bottomSheetBinding.btnAccept.setOnClickListener {
            bottomSheetDialog.dismiss()

            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", requireContext().packageName, null)
            )
            startActivity(intent)
        }

        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun checkGalleryPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (checkPermissionGranted(GALLERY_PERMISSION)) {
                pickImageLauncher.launch("image/*")
            } else {
                galleryPermissionLauncher.launch(GALLERY_PERMISSION)
            }
        } else {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun checkCameraPermission() {
        if (checkPermissionGranted(CAMERA_PERMISSION)) {
            openCamera()
        } else {
            cameraPermissionLauncher.launch(CAMERA_PERMISSION)
        }
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        photoFile?.let {
            currentPhotoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                it
            )
            takePictureLauncher.launch(currentPhotoUri)
        }
    }

    private fun checkPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        return imageFile
    }

    private val galleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                pickImageLauncher.launch("image/*")
            } else {
                showBottomSheetPermissionDenied()
            }
        }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                showBottomSheetPermissionDenied()
            }
        }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            updateImageUser(uri = it)
        }
    }

    private val pickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            updateImageUser(uri = it)
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePicture ->
        if (didTakePicture) {
            updateImageUser(uri = currentPhotoUri)
        }
    }

    private fun updateImageUser(uri: Uri?) {
        currentPhotoUri = uri
        binding.imageProfile.setImageURI(uri)
        binding.tvPhotoEmpty.isVisible = false
        binding.imageProfile.isVisible = true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            Glide
                .with(requireContext())
                .load(R.drawable.loading)
                .into(binding.progressBar)

            binding.progressBar.isVisible = true
        } else {
            binding.progressBar.isVisible = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}