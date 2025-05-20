package com.rpla.marsrovernavigator.gridnavigator.viewmodel

import com.rpla.marsrovernavigator.ui.base.ViewState

sealed class GridNavigatorState : ViewState {
    data object Initial : GridNavigatorState()

    data object ProcessCommands : GridNavigatorState()

    class UpdateRoverPosition(val roverPosition: Pair<Int, Int>) : GridNavigatorState()
}
