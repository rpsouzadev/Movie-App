package com.rpsouza.movieapp.data.mapper

import com.rpsouza.movieapp.data.model.cast.CastResponse
import com.rpsouza.movieapp.data.model.gener.GenreResponse
import com.rpsouza.movieapp.data.model.movie.CountryResponse
import com.rpsouza.movieapp.data.model.movie.MovieResponse
import com.rpsouza.movieapp.data.model.review.AuthorDetailsResponse
import com.rpsouza.movieapp.data.model.review.MovieReviewResponse
import com.rpsouza.movieapp.domain.model.cast.Cast
import com.rpsouza.movieapp.domain.model.gener.Genre
import com.rpsouza.movieapp.domain.model.movie.Country
import com.rpsouza.movieapp.domain.model.movie.Movie
import com.rpsouza.movieapp.domain.model.review.AuthorDetails
import com.rpsouza.movieapp.domain.model.review.MovieReview
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
    posterPath = "https://image.tmdb.org/t/p/w500${this.posterPath}",
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

fun CastResponse.toDomain(): Cast {
  return Cast(
    adult = this.adult,
    castId = this.castId,
    character = this.character,
    creditId = this.creditId,
    gender = this.gender,
    id = this.id,
    knownForDepartment = this.knownForDepartment,
    name = this.name,
    order = this.order,
    originalName = this.originalName,
    popularity = this.popularity,
    profilePath = "https://image.tmdb.org/t/p/w500${this.profilePath}",
  )
}

fun MovieReviewResponse.toDomain(): MovieReview {
  return MovieReview(
    author = this.author,
    authorDetails = this.authorDetails?.toDomain(),
    content = this.content,
    createdAt = this.createdAt,
    id = this.id,
    updatedAt = this.updatedAt,
    url = this.url
  )
}

fun AuthorDetailsResponse.toDomain(): AuthorDetails {
  return AuthorDetails(
    avatarPath = this.avatarPath,
    username = this.username,
    name = this.name,
    rating = this.rating
  )
}