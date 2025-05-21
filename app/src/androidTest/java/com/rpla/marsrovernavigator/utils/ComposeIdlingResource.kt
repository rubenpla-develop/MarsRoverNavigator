package com.rpla.marsrovernavigator.utils

import androidx.compose.ui.test.IdlingResource

class ComposeIdlingResource: IdlingResource {

    private var isAppIdle = false

    fun isAppIdle(isIdle: Boolean) {
        isAppIdle = isIdle
    }

    override val isIdleNow: Boolean
        get() = isAppIdle
}
