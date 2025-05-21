package com.rpla.marsrovernavigator.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<INTENT : ViewIntent, ACTION : ViewAction, STATE : ViewState> : ViewModel() {
    private val _state: MutableStateFlow<STATE> by lazy { MutableStateFlow(createInitialState()) }
    val state = _state.asStateFlow()

    fun dispatchIntent(intent: INTENT) {
        handleAction(mapIntentToAction(intent = intent))
    }

    fun setState(state: STATE) {
        _state.value = state
    }

    abstract fun createInitialState(): STATE

    abstract fun mapIntentToAction(intent: INTENT): ACTION

    abstract fun handleAction(action: ACTION)
}
