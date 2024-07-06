package com.rpsouza.movieapp.domain.model.menuProfile

import com.rpsouza.movieapp.R

data class MenuProfile(
    val name: Int,
    val icon: Int,
    val type: MenuProfileType
) {
    companion object {
        val menuItems = listOf(
            MenuProfile(
                R.string.text_edit_profile_profile_fragment,
                R.drawable.ic_profile,
                MenuProfileType.PROFILE
            ),
            MenuProfile(
                R.string.text_notificate_profile_fragment,
                R.drawable.ic_notification,
                MenuProfileType.NOTIFICATION
            ),
            MenuProfile(
                R.string.text_download_profile_fragment,
                R.drawable.ic_download_profile,
                MenuProfileType.DOWNLOAD
            ),
            MenuProfile(
                R.string.text_security_profile_fragment,
                R.drawable.ic_security,
                MenuProfileType.SECURITY
            ),
            MenuProfile(
                R.string.text_language_profile_fragment,
                R.drawable.ic_language,
                MenuProfileType.LANGUAGE
            ),
            MenuProfile(
                R.string.text_dark_mode_profile_fragment,
                R.drawable.ic_dark_mode,
                MenuProfileType.DARK_MODE
            ),
            MenuProfile(
                R.string.text_helper_center_profile_fragment,
                R.drawable.ic_info,
                MenuProfileType.HELPER
            ),
            MenuProfile(
                R.string.text_privacy_policy_profile_fragment,
                R.drawable.ic_privacy_policy,
                MenuProfileType.PRIVACY_POLICY
            ),
            MenuProfile(
                R.string.text_logout_profile_fragment,
                R.drawable.ic_logout,
                MenuProfileType.LOGOUT
            )
        )
    }
}

enum class MenuProfileType {
    PROFILE,
    NOTIFICATION,
    DOWNLOAD,
    SECURITY,
    LANGUAGE,
    DARK_MODE,
    HELPER,
    PRIVACY_POLICY,
    LOGOUT,
}