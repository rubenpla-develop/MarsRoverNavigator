package com.rpla.marsrovernavigator.navigator.viewmodel

import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.ui.presentation.NavigatorUiState
import com.rpla.marsrovernavigator.ui.base.ViewIntent

sealed class NavigatorIntent : ViewIntent {
    data object BuildDefault : NavigatorIntent()

    data class ProcessCommands(val coordinatesData: NavigatorConfig) : NavigatorIntent()

    data class UpdateRoverPosition(val uiState: NavigatorUiState) : NavigatorIntent()
}
