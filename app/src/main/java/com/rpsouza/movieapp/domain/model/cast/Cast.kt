package com.rpsouza.movieapp.domain.model.cast

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
  val adult: Boolean?,
  val castId: Int?,
  val character: String?,
  val creditId: String?,
  val gender: Int?,
  val id: Int?,
  val knownForDepartment: String?,
  val name: String?,
  val order: Int?,
  val originalName: String?,
  val popularity: Float?,
  val profilePath: String?
) : Parcelable