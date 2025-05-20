package com.rpla.marsrovernavigator.navigator.viewmodel

import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.ui.base.ViewAction

sealed class NavigatorAction : ViewAction {
    data object BuildDefault : NavigatorAction()

    data class ProcessCommands(val config: NavigatorConfig) : NavigatorAction()

    data object UpdateRoverPosition : NavigatorAction()
}
