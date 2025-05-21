@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.navigator.ui.commandcontroller

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.RotateLeft
import androidx.compose.material.icons.automirrored.filled.RotateRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rpla.marsrovernavigator.R
import com.rpla.marsrovernavigator.navigator.data.model.Coordinates
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorDirection
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorCurrentState
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorIntent
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorViewModel
import com.rpla.marsrovernavigator.ui.theme.MarsRoverNavigatorTheme
import com.rpla.marsrovernavigator.ui.theme.White

@Composable
fun NavigatorController(
    modifier: Modifier,
    viewModel: NavigatorViewModel,
    currentState: NavigatorCurrentState,
) {
    var commands by rememberSaveable { mutableStateOf("") }
    Column(
        modifier =
            modifier
                .wrapContentSize()
                .fillMaxWidth(),
    ) {
        Text(
            text = commands,
            textAlign = TextAlign.Center,
            color = Color.Blue,
            fontSize = 36.sp,
            modifier =
                modifier
                    .background(Color.Transparent)
                    .fillMaxWidth(),
        )

        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                IconButton(
                    onClick = {
                        commands += "L"
                        Log.i("Commands", "Commands: $commands")
                    },
                    modifier =
                        Modifier
                            .testTag(stringResource(R.string.command_palette_rotate_right_test_tag)),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.RotateLeft,
                        contentDescription = stringResource(R.string.command_palette_rotate_left_content_description),
                        tint = White,
                    )
                }

                IconButton(
                    onClick = {
                        commands += "R"
                        Log.i("Commands", "Commands: $commands")
                    },
                    modifier = Modifier.testTag(stringResource(R.string.command_palette_rotate_left_test_tag)),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.RotateRight,
                        contentDescription = stringResource(R.string.command_palette_rotate_right_content_description),
                        tint = White,
                    )
                }

                IconButton(
                    onClick = {
                        commands += "M"
                        Log.i("Commands", "Commands: $commands")
                    },
                    modifier = Modifier.testTag(stringResource(R.string.command_palette_move_position_test_tag)),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.DirectionsRun,
                        contentDescription = stringResource(R.string.command_palette_move_position_content_description),
                        tint = White,
                    )
                }

                IconButton(
                    onClick = {
                        val dataConfig =
                            NavigatorConfig(
                                topRightCorner = Coordinates(currentState.gridSize, currentState.gridSize),
                                roverPosition = Coordinates(currentState.x, currentState.y),
                                roverNavigatorDirection = currentState.navigatorDirection,
                                commands,
                            )
                        viewModel.dispatchIntent(NavigatorIntent.ProcessCommands(coordinatesData = dataConfig))
                        Log.i("Commands", "Commands: $commands")
                    },
                    modifier = Modifier.testTag(stringResource(R.string.command_palette_process_commands_test_tag)),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription =
                            stringResource(
                                R.string.command_palette_process_commands_content_description,
                            ),
                        tint = Color.Green,
                    )
                }

                IconButton(
                    onClick = {
                        commands = ""
                    },
                    modifier = Modifier.testTag(stringResource(R.string.command_palette_reset_commands_test_tag)),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Restore,
                        contentDescription =
                            stringResource(
                                R.string.command_palette_reset_commands_content_description,
                            ),
                        tint = Color.Red,
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommandControllerPreview() {
    MarsRoverNavigatorTheme {
        NavigatorController(
            Modifier,
            hiltViewModel(),
            NavigatorCurrentState(
                x = 1,
                y = 2,
                gridSize = 5,
                navigatorDirection = NavigatorDirection.W,
            ),
        )
    }
}
