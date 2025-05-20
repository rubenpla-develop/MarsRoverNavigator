package com.rpla.marsrovernavigator.navigator.domain.usecase

import com.rpla.marsrovernavigator.domain.base.BaseUseCase
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig

class GetNavigatorCoordinatesUseCase :
    BaseUseCase<GetNavigatorCoordinatesUseCase.RequestValue, String>() {
    override suspend fun run(request: RequestValue): String {
        // Repository & Mapper logic
        TODO("Not yet implemented")
    }

    data class RequestValue(
        val currentCoordinates: NavigatorConfig,
    )
}
