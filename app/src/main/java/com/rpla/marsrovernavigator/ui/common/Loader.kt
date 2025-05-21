@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rpla.marsrovernavigator.R
import com.rpla.marsrovernavigator.ui.theme.PinkA400
import kotlinx.coroutines.delay

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier,
    durationMillis: Long = 1_000L,
    onTimeout: () -> Unit = {},
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(durationMillis)
        visible = false
        onTimeout()
    }

    if (visible) {
        CircularProgressIndicator(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
                    .wrapContentSize(Alignment.Center)
                    .testTag(stringResource(R.string.loader_test_tag)),
            color = PinkA400,
        )
    }
}
