package com.rpla.marsrovernavigator.navigator.viewmodel

import com.rpla.marsrovernavigator.navigator.ui.presentation.NavigatorUiState
import com.rpla.marsrovernavigator.ui.base.ViewState

sealed class NavigatorState : ViewState {
    data object Initial : NavigatorState()

    class ProcessCommands(val newRoverPosition: NavigatorUiState) : NavigatorState()

    class UpdateRoverPosition(val roverPosition: NavigatorUiState) : NavigatorState()
}
