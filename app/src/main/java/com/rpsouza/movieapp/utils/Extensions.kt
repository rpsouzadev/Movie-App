package com.rpsouza.movieapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.snackbar.Snackbar
import com.rpsouza.movieapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Fragment.initToolbar(
    toolbar: Toolbar,
    showIconNavigation: Boolean = true,
    lightIcon: Boolean = false
) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""

    val iconBack = if (lightIcon) R.drawable.ic_back_light else R.drawable.ic_back

    if (showIconNavigation) {
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(iconBack)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    toolbar.setNavigationOnClickListener {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}

fun Fragment.hideKeyboard() {
    val view = activity?.currentFocus
    if (view != null) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}

fun Fragment.showSnackBar(
    message: Int,
    duration: Int = Snackbar.LENGTH_LONG
) {
    view?.let { Snackbar.make(it, message, duration).show() }
}

fun String.isEmailValid(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return emailPattern.matches(this)
}

fun formatCommentDate(date: String?): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val providedDate = date?.let { dateFormat.parse(it) }
    val currentDate = Date()

    val calendarProvided = Calendar.getInstance()
    val calendarCurrent = Calendar.getInstance()
    providedDate?.let { calendarProvided.time = it }
    calendarCurrent.time = currentDate

    val yearDifference = calendarCurrent.get(Calendar.YEAR) - calendarProvided.get(Calendar.YEAR)
    val monthDifference = calendarCurrent.get(Calendar.MONTH) - calendarProvided.get(Calendar.MONTH)
    val dayDifference =
        calendarCurrent.get(Calendar.DAY_OF_MONTH) - calendarProvided.get(Calendar.DAY_OF_MONTH)

    val totalDaysDifference = yearDifference * 365 + monthDifference * 30 + dayDifference

    return when {
        totalDaysDifference == 0 -> "Hoje"
        totalDaysDifference == 1 -> "Ontem"
        totalDaysDifference < 31 -> "$totalDaysDifference dias atrás"
        else -> {
            val monthsDifference = totalDaysDifference / 30
            if (monthsDifference == 1) {
                "1 mês atrás"
            } else {
                "$monthsDifference meses atrás"
            }
        }
    }
}

fun Double.calculateFileSize(): String {
    val value = this * 10.0

    return if (value >= 1000) {
        String.format("%.2f GB", value / 1000)
    } else {
        String.format("%.1f MB", value)
    }
}

fun Int.calculateMovieTime(): String {
    val hours = this / 60
    val minutes = this % 60
    return "${hours}h ${minutes}m"
}

fun Context.circularProgressDrawable(): Drawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = 12f
        centerRadius = 60f
        setColorSchemeColors(
            ContextCompat.getColor(
                this@circularProgressDrawable,
                R.color.color_default
            )
        )
        start()
    }
}

fun NavController.animNavigate(action: Int) {
    this.navigate(
        action,
        null,
        NavOptions.Builder()
            .setExitAnim(R.anim.exit)
            .setEnterAnim(R.anim.enter)
            .setPopExitAnim(R.anim.pop_exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .build()
    )
}

fun NavController.animNavigate(action: NavDirections) {
    this.navigate(
        action,
        NavOptions.Builder()
            .setExitAnim(R.anim.exit)
            .setEnterAnim(R.anim.enter)
            .setPopExitAnim(R.anim.pop_exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .build()
    )
}