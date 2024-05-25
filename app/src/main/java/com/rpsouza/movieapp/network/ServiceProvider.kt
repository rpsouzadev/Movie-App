package com.rpsouza.movieapp.network

import com.rpsouza.movieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceProvider {

  private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    })
    .addInterceptor(Interceptor { chain ->
      chain.run {
        proceed(
          request()
            .newBuilder()
            .addHeader(
              name = "Authorization",
              value = BuildConfig.THE_MOVIE_DB_KEY
            )
            .build()
        )
      }
    })
    .build()

  private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

  fun <API> createService(apiClass: Class<API>): API = retrofit.create(apiClass)
}