@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.ui.common

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.rpla.marsrovernavigator.R
import com.rpla.marsrovernavigator.ui.theme.PinkA400
import com.rpla.marsrovernavigator.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    slotComposable: @Composable () -> Unit,
    contextualAction: () -> Unit,
    modifier: Modifier,
) {
    val context = LocalContext.current
    TopAppBar(
        title = {
            slotComposable()
        },
        actions = {
            IconButton(
                onClick = {
                    Toast.makeText(context, "Contextual Action", Toast.LENGTH_SHORT).show()
                    contextualAction()
                },
                modifier = Modifier.testTag(stringResource(R.string.home_app_bar_json_data_test_tag)),
            ) {
                Icon(
                    imageVector = Icons.Filled.DataObject,
                    contentDescription = "Filter",
                    tint = White,
                )
            }
        },
        modifier = modifier.testTag(stringResource(R.string.home_app_bar_test_tag)),
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = PinkA400,
                titleContentColor = White,
            ),
    )
}
