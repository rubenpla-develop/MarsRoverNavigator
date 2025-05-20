@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.gridnavigator.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.rpla.marsrovernavigator.gridnavigator.viewmodel.GridNavigatorIntent
import com.rpla.marsrovernavigator.gridnavigator.viewmodel.GridNavigatorState
import com.rpla.marsrovernavigator.gridnavigator.viewmodel.RoverNavigatorViewModel

@Composable
fun GridNavigatorUI(
    viewModel: RoverNavigatorViewModel = hiltViewModel(),
    innerPaddings: PaddingValues,
) {
    val roverPosition = rememberUpdatedState(Pair(2, 1))

    LaunchedEffect(Unit) {
        viewModel.dispatchIntent(GridNavigatorIntent.BuildDefaultGrid)
    }

    val uiState = viewModel.state.collectAsState()

    when (uiState.value) {
        is GridNavigatorState.Initial, is GridNavigatorState.UpdateRoverPosition -> {
            GridDisplay(
                Modifier
                    .background(color = Color.Gray),
                innerPaddings = innerPaddings,
                roverPosition = roverPosition,
                viewModel = viewModel,
            )
        }

        GridNavigatorState.ProcessCommands -> TODO()
    }
}
