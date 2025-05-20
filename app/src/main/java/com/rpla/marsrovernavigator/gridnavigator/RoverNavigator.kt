@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.gridnavigator

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rpla.marsrovernavigator.R
import com.rpla.marsrovernavigator.gridnavigator.ui.grid.GridNavigatorUI
import com.rpla.marsrovernavigator.ui.common.HomeAppBar
import com.rpla.marsrovernavigator.ui.theme.MarsRoverNavigatorTheme

@Composable
fun RoverNavigator() {
    Scaffold(
        topBar = {
            HomeAppBar(
                slotComposable = { Text(text = stringResource(R.string.app_name)) },
                modifier = Modifier,
                contextualAction = {
                    // TODO launch data input
                },
            )
        },
        content = { innerPadding ->
            GridNavigatorUI(innerPaddings = innerPadding)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun RoverNavigatorPreview() {
    MarsRoverNavigatorTheme {
        RoverNavigator()
    }
}
