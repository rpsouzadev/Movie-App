package com.rpsouza.movieapp.data.mapper

import com.rpsouza.movieapp.data.model.gener.GenreResponse
import com.rpsouza.movieapp.data.model.movie.CountryResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse
import com.rpsouza.movieapp.domain.model.gener.Genre
import com.rpsouza.movieapp.domain.model.movie.Country
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.presenter.model.GenrePresentation

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
    genres = this.genres?.map { it.toDomain() },
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
    voteCount = this.voteCount,
    productionCountries = this.productionCountries?.map { it.toDomain() }
  )
}

fun Genre.toPresentation(): GenrePresentation {
  return GenrePresentation(
    id = this.id,
    name = this.name,
    movies = emptyList()
  )
}

fun CountryResponse.toDomain(): Country {
  return Country(name = this.name)
}
