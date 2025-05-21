package com.rpla.marsrovernavigator.navigator.domain.usecase

import com.rpla.marsrovernavigator.domain.base.BaseUseCase
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.domain.entity.ProcessedCoordinatesEntity
import com.rpla.marsrovernavigator.navigator.domain.repository.CoordinatesRepository
import javax.inject.Inject

class GetNavigatorCoordinatesUseCase
    @Inject
    constructor(private val coordinatesRepository: CoordinatesRepository) :
    BaseUseCase<GetNavigatorCoordinatesUseCase.RequestValue, ProcessedCoordinatesEntity>() {
        override suspend fun run(request: RequestValue): ProcessedCoordinatesEntity {
            // Repository & Mapper logic
            // obtain raw string coordinates & map to ProcessedCoordinatesEntity
            return coordinatesRepository.getNewCoordinates(request.currentCoordinates)
            // val newCoord = ProcessedCoordinatesEntity(rawCoordinates = "3 4 W")
            // return newCoord
        }

        data class RequestValue(
            val currentCoordinates: NavigatorConfig,
        )
    }
