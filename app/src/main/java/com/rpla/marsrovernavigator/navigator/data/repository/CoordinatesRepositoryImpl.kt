package com.rpla.marsrovernavigator.navigator.data.repository

import com.rpla.marsrovernavigator.navigator.data.mapper.NavigatorConfigMapper
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.domain.entity.ProcessedCoordinatesEntity
import com.rpla.marsrovernavigator.navigator.domain.repository.CoordinatesRepository

class CoordinatesRepositoryImpl : CoordinatesRepository {
    override suspend fun getNewCoordinates(currentCoordinates: NavigatorConfig): ProcessedCoordinatesEntity {
        return NavigatorConfigMapper.mapToProcessedEntity(currentCoordinates)
    }
}
