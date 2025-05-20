package com.rpla.marsrovernavigator.navigator.data.model

data class NavigatorConfig(
    val topRightCorner: Coordinates,
    val roverPosition: Coordinates,
    val roverNavigationDirection: NavigationDirection,
    val movements: String,
)

data class Coordinates(
    val x: Int,
    val y: Int,
)

data class NavigatorCurrentState(
    val x: Int,
    val y: Int,
    val gridSize: Int,
    val navigationDirection: NavigationDirection,
)
