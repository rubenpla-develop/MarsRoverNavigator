package com.rpla.marsrovernavigator.navigator.data.mapper

import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorDirection
import com.rpla.marsrovernavigator.navigator.domain.entity.ProcessedCoordinatesEntity

object NavigatorConfigMapper {
    fun mapToProcessedEntity(config: NavigatorConfig): ProcessedCoordinatesEntity {
        val maximumCoordinateX = config.topRightCorner.x.minus(1)
        val maximumCoordinateY = config.topRightCorner.y.minus(1)

        var x = config.roverPosition.x
        var y = config.roverPosition.y
        var direction = config.roverNavigatorDirection

        for (movement in config.movements) {
            when (movement) {
                'L' -> {
                    direction = direction.rotateLeft()
                }
                'R' -> {
                    direction = direction.rotateRight()
                }
                'M' -> {
                    val (nx, ny) =
                        when (direction) {
                            NavigatorDirection.N -> x to (y + 1)
                            NavigatorDirection.E -> (x + 1) to y
                            NavigatorDirection.S -> x to (y - 1)
                            NavigatorDirection.W -> (x - 1) to y
                        }
                    if (nx in 0..maximumCoordinateX && ny in 0..maximumCoordinateY) {
                        x = nx
                        y = ny
                    }
                }
                else -> {
                    // TODO Throw exception?
                }
            }
        }

        // Output formatting to "x y D"
        val raw = "$x $y ${direction.symbol}"
        return ProcessedCoordinatesEntity(rawCoordinates = raw)
    }
}
