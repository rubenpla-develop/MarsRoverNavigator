package com.rpla.marsrovernavigator.gridnavigator.viewmodel

import com.rpla.marsrovernavigator.ui.base.ViewIntent

sealed class GridNavigatorIntent : ViewIntent {
    data object BuildDefaultGrid : GridNavigatorIntent()

    data object ProcessCommands : GridNavigatorIntent()
}
