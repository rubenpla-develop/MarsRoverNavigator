package com.rpla.marsrovernavigator.core.di

import com.rpla.marsrovernavigator.navigator.data.repository.CoordinatesRepositoryImpl
import com.rpla.marsrovernavigator.navigator.domain.repository.CoordinatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providesCoordinatesRepository(): CoordinatesRepository = CoordinatesRepositoryImpl()
}
