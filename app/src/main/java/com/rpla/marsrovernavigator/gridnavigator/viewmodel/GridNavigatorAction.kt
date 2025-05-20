package com.rpla.marsrovernavigator.gridnavigator.viewmodel

import com.rpla.marsrovernavigator.ui.base.ViewAction

sealed class GridNavigatorAction : ViewAction {
    data object BuildDefaultGrid : GridNavigatorAction()

    data object UpdateRoverPosition : GridNavigatorAction()
}
