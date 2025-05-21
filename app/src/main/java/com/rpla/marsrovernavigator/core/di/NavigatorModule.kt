package com.rpla.marsrovernavigator.core.di

import com.rpla.marsrovernavigator.navigator.domain.repository.CoordinatesRepository
import com.rpla.marsrovernavigator.navigator.domain.usecase.GetNavigatorCoordinatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NavigatorModule {
    @Provides
    fun providesGetNavigatorCoordinatesUseCase(coordinatesRepository: CoordinatesRepository) =
        GetNavigatorCoordinatesUseCase(coordinatesRepository)
}
