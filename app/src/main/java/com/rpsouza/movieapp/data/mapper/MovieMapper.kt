package com.rpsouza.movieapp.data.mapper

import com.rpsouza.movieapp.data.model.gener.GenreResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse
import com.rpsouza.movieapp.domain.model.gener.Genre
import com.rpsouza.movieapp.domain.model.movie.Movie

fun GenreResponse.toDomain(): Genre {
  return Genre(
    id = this.id,
    name = this.name
  )
}

fun MovieResponse.toDomain(): Movie {
  return Movie(
    adult = this.adult,
    backdropPath = this.backdropPath,
    genreIds = this.genreIds,
    id = this.id,
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    video = this.video,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount
  )
}