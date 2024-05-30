package com.rpsouza.movieapp.di

import com.rpsouza.movieapp.data.api.ServiceAPI
import com.rpsouza.movieapp.network.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

  @Provides
  fun providesServiceProvider() = ServiceProvider()

  @Provides
  @Singleton
  fun providerServiceAPI(
    serviceProvider: ServiceProvider
  ): ServiceAPI {
    return serviceProvider.createService(ServiceAPI::class.java)
  }
}