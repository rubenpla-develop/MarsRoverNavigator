package com.rpla.marsrovernavigator.navigator.domain.repository

import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.domain.entity.ProcessedCoordinatesEntity

interface CoordinatesRepository {
    suspend fun getNewCoordinates(currentCoordinates: NavigatorConfig): ProcessedCoordinatesEntity
}
