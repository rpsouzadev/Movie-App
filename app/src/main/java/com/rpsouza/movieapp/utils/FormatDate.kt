package com.rpsouza.movieapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

class FormatDate {

  companion object {
    fun yearFormat(dateMovie: String): String {
      val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
      val date = originalFormat.parse(dateMovie)
      val year = SimpleDateFormat("yyyy", Locale.ROOT)
      return year.format(date ?: "")
    }
  }
}