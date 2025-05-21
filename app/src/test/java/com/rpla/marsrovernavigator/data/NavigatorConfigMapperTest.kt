package com.rpla.marsrovernavigator.data

import com.rpla.marsrovernavigator.navigator.data.mapper.NavigatorConfigMapper
import com.rpla.marsrovernavigator.navigator.data.model.Coordinates
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorDirection
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigatorConfigMapperTest {
    @Test
    fun `Given M command with initial direction N should move forward`() {
        val config =
            NavigatorConfig(
                topRightCorner = Coordinates(5, 5),
                roverPosition = Coordinates(2, 2),
                roverNavigatorDirection = NavigatorDirection.N,
                movements = "M",
            )

        val result = NavigatorConfigMapper.mapToProcessedEntity(config)
        assertEquals("2 3 N", result.rawCoordinates)
    }

    @Test
    fun `Given rotation commands only should change direction but not position`() {
        val config =
            NavigatorConfig(
                topRightCorner = Coordinates(5, 5),
                roverPosition = Coordinates(1, 1),
                roverNavigatorDirection = NavigatorDirection.N,
                movements = "RRL",
            )

        val result = NavigatorConfigMapper.mapToProcessedEntity(config)
        assertEquals("1 1 E", result.rawCoordinates)
    }

    @Test
    fun `Given rover starts at edge and moves inside the grid should update correctly`() {
        val config =
            NavigatorConfig(
                topRightCorner = Coordinates(5, 5),
                roverPosition = Coordinates(0, 0),
                roverNavigatorDirection = NavigatorDirection.N,
                movements = "MMRMM",
            )

        val result = NavigatorConfigMapper.mapToProcessedEntity(config)
        assertEquals("2 2 E", result.rawCoordinates)
    }

    @Test
    fun `Given command that would exceed plateau limit should not move`() {
        val config =
            NavigatorConfig(
                topRightCorner = Coordinates(3, 3),
                roverPosition = Coordinates(2, 2),
                roverNavigatorDirection = NavigatorDirection.N,
                movements = "MMM",
            )

        val result = NavigatorConfigMapper.mapToProcessedEntity(config)
        assertEquals("2 2 N", result.rawCoordinates)
    }

    @Test
    fun `Given a full command sequence should reach expected position`() {
        val config =
            NavigatorConfig(
                topRightCorner = Coordinates(6, 6),
                roverPosition = Coordinates(1, 2),
                roverNavigatorDirection = NavigatorDirection.N,
                movements = "LMLMLMLMM",
            )

        val result = NavigatorConfigMapper.mapToProcessedEntity(config)
        assertEquals("1 3 N", result.rawCoordinates)
    }

    @Test
    fun `Given a full command sequence with mixed directions should reach expected position`() {
        val config =
            NavigatorConfig(
                topRightCorner = Coordinates(5, 5),
                roverPosition = Coordinates(1, 2),
                roverNavigatorDirection = NavigatorDirection.N,
                movements = "LMLMLMRMMRMM",
            )

        val result = NavigatorConfigMapper.mapToProcessedEntity(config)
        assertEquals("0 0 W", result.rawCoordinates)
    }
}
