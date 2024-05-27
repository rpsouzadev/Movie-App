package com.rpsouza.movieapp.domain.model.gener

import com.rpsouza.movieapp.data.model.gener.GenreResponse


data class GenreListResponse(
  val genres: List<GenreResponse>?
)
