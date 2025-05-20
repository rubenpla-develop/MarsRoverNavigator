@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.gridnavigator.ui.grid

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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import com.rpla.marsrovernavigator.gridnavigator.ui.commandcontroller.CommandsController
import com.rpla.marsrovernavigator.gridnavigator.viewmodel.RoverNavigatorViewModel
import com.rpla.marsrovernavigator.ui.theme.Black

@Composable
fun GridDisplay(
    modifier: Modifier,
    innerPaddings: PaddingValues,
    roverPosition: State<Pair<Int, Int>>,
    viewModel: RoverNavigatorViewModel,
) {
    val gridSize = rememberSaveable { 5 }
    val totalCells = rememberSaveable { gridSize * gridSize }

    ConstraintLayout(
        modifier = modifier.fillMaxSize(),
    ) {
        val (grid, commandsController) = createRefs()

        LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(gridSize),
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
                val x = index % gridSize
                val y = gridSize - 1 - index / gridSize // Y invertido para tener (0,0) abajo

                Box(
                    modifier =
                        Modifier
                            .aspectRatio(1f)
                            .border(1.dp, Color.Gray)
                            .background(
                                if (x == roverPosition.value.first && y == roverPosition.value.second) {
                                    Color.Red
                                } else {
                                    Color.White
                                },
                            ),
                ) {
                    if (x == roverPosition.value.first && y == roverPosition.value.second) {
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

        CommandsController(
            modifier =
                modifier
                    .wrapContentSize()
                    .padding(bottom = innerPaddings.calculateBottomPadding())
                    .constrainAs(commandsController) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            viewModel = viewModel,
        )
    }
}
