package com.rpsouza.movieapp.data.model.credits

import com.rpsouza.movieapp.data.model.cast.CastResponse

data class CreditsResponse(
  val id: Int?,
  val cast: List<CastResponse>?
)
