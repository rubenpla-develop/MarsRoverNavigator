package com.rpla.marsrovernavigator.ui.navigation

sealed class Routes(val route: String) {
    data object RoverNavigation : Routes("RoverNavigation")

    data object LastDataInput : Routes("LastDataInput?json={input}") {
        fun createRoute(input: String) = "LastDataInput?json=$input"
    }
}

const val LAST_DATA_INPUT_PARAM_NAME = "input"
