@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.navigator.ui.grid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.rpla.marsrovernavigator.R
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorCurrentState
import com.rpla.marsrovernavigator.navigator.ui.commandcontroller.NavigatorController
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorViewModel
import com.rpla.marsrovernavigator.ui.theme.Black

@Composable
fun NavigatorComponents(
    modifier: Modifier,
    innerPaddings: PaddingValues,
    currentGridState: NavigatorCurrentState,
    viewModel: NavigatorViewModel,
) {
    val totalCells = rememberSaveable { currentGridState.gridSize * currentGridState.gridSize }

    ConstraintLayout(
        modifier = modifier.fillMaxSize(),
    ) {
        val (grid, commandsController) = createRefs()

        LazyVerticalGrid(
            columns = Fixed(currentGridState.gridSize),
            modifier =
                modifier
                    .wrapContentSize()
                    .padding(top = innerPaddings.calculateTopPadding())
                    .constrainAs(grid) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
        ) {
            items(totalCells) { index ->
                val x = index % currentGridState.gridSize
                val y = currentGridState.gridSize - 1 - index / currentGridState.gridSize

                Box(
                    modifier =
                        Modifier
                            .aspectRatio(1f)
                            .border(1.dp, Color.Gray)
                            .background(
                                if (x == currentGridState.x && y == currentGridState.y) {
                                    Color.Red
                                } else {
                                    Color.White
                                },
                            ),
                ) {
                    if (x == currentGridState.x && y == currentGridState.y) {
                        Image(
                            modifier =
                                Modifier
                                    .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                            painter =
                                rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current).data(data = R.drawable.mars_rover_splash)
                                        .apply(block = fun ImageRequest.Builder.() {
                                            placeholder(R.drawable.ic_launcher_foreground)
                                            error(R.drawable.ic_launcher_foreground)
                                            crossfade(true)
                                        }).build(),
                                ),
                            contentDescription = stringResource(R.string.grid_cell_rover_position_content_description),
                        )
                    } else {
                        Icon(
                            modifier =
                                Modifier.align(Alignment.Center)
                                    .size(32.dp),
                            imageVector = Icons.Filled.Landscape,
                            contentDescription = stringResource(R.string.gird_cell_empty_position_content_description),
                            tint = Black,
                        )
                    }
                }
            }
        }

        NavigatorController(
            modifier =
                modifier
                    .wrapContentSize()
                    .padding(bottom = innerPaddings.calculateBottomPadding())
                    .constrainAs(commandsController) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            currentState = currentGridState,
            viewModel = viewModel,
        )
    }
}
