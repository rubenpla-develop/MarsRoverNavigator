@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.navigator.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorCurrentState
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorDirection
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorIntent
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorState
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorViewModel
import com.rpla.marsrovernavigator.ui.common.LoadingItem

@Composable
fun NavigatorUI(
    viewModel: NavigatorViewModel = hiltViewModel(),
    innerPaddings: PaddingValues,
) {
    // Default Coordinates
    var roverPosition = rememberUpdatedState(Pair(2, 1))

    // Default orientation
    var orientation = rememberUpdatedState(NavigatorDirection.N)

    // Default Grid Size
    val gridSize = rememberUpdatedState(5)

    LaunchedEffect(Unit) {
        viewModel.dispatchIntent(NavigatorIntent.BuildDefault)
    }

    val uiState = viewModel.state.collectAsState()

    when (uiState.value) {
        is NavigatorState.Initial -> {
            NavigatorComponents(
                Modifier
                    .background(color = Color.Gray),
                innerPaddings = innerPaddings,
                currentGridState =
                    NavigatorCurrentState(
                        x = roverPosition.value.first,
                        y = roverPosition.value.second,
                        gridSize = gridSize.value,
                        navigatorDirection = orientation.value,
                    ),
                viewModel = viewModel,
            )
        }

        is NavigatorState.ProcessCommands -> {
            LoadingItem(onTimeout = {
                viewModel.dispatchIntent(
                    NavigatorIntent.UpdateRoverPosition(
                        (uiState.value as NavigatorState.ProcessCommands).newRoverPosition,
                    ),
                )
            })
        }

        is NavigatorState.UpdateRoverPosition -> {
            roverPosition =
                rememberUpdatedState(
                    Pair(
                        first =
                            (uiState.value as NavigatorState.UpdateRoverPosition)
                                .roverPosition.x,
                        second =
                            (uiState.value as NavigatorState.UpdateRoverPosition)
                                .roverPosition.y,
                    ),
                )

            orientation =
                rememberUpdatedState(
                    NavigatorDirection.valueOf(
                        (uiState.value as NavigatorState.UpdateRoverPosition)
                            .roverPosition.direction,
                    ),
                )

            NavigatorComponents(
                Modifier
                    .background(color = Color.Gray),
                innerPaddings = innerPaddings,
                currentGridState =
                    NavigatorCurrentState(
                        x = roverPosition.value.first,
                        y = roverPosition.value.second,
                        gridSize = gridSize.value,
                        navigatorDirection = orientation.value,
                    ),
                viewModel = viewModel,
            )
        }
    }
}
