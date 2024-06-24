package com.rpsouza.movieapp.data.remote.model.credits

import com.rpsouza.movieapp.data.remote.model.cast.CastResponse

data class CreditsResponse(
  val id: Int?,
  val cast: List<CastResponse>
)
