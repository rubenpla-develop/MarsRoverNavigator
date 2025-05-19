@file:Suppress("ktlint:standard:function-naming")

package com.rpla.marsrovernavigator.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rpla.marsrovernavigator.R
import com.rpla.marsrovernavigator.ui.theme.PinkA400

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
                .testTag(stringResource(R.string.loader_test_tag)),
        color = PinkA400,
    )
}
