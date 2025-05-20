package com.rpla.marsrovernavigator.navigator.data.model

enum class NavigationDirection(val symbol: String) {
    N("N"),
    E("E"),
    S("S"),
    W("W"),
    ;

    fun rotateLeft(): NavigationDirection = entries[(ordinal + 3) % 4]

    fun rotateRight(): NavigationDirection = entries[(ordinal + 1) % 4]
}
