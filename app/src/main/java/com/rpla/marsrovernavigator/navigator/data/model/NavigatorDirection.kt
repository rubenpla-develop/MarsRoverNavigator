package com.rpla.marsrovernavigator.navigator.data.model

enum class NavigatorDirection(val symbol: String) {
    N("N"),
    E("E"),
    S("S"),
    W("W"),
    ;

    fun rotateLeft(): NavigatorDirection = entries[(ordinal + 3) % 4]

    fun rotateRight(): NavigatorDirection = entries[(ordinal + 1) % 4]
}
