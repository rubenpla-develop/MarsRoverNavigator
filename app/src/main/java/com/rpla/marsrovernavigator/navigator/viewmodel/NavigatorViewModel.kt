package com.rpla.marsrovernavigator.navigator.viewmodel

import com.rpla.marsrovernavigator.navigator.data.model.NavigatorDirection
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.ui.presentation.NavigatorUiState
import com.rpla.marsrovernavigator.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigatorViewModel
    @Inject
    constructor() :
    BaseViewModel<NavigatorIntent, NavigatorAction, NavigatorState>() {
        override fun createInitialState(): NavigatorState = NavigatorState.Initial

        override fun handleAction(action: NavigatorAction) {
            when (action) {
                is NavigatorAction.BuildDefault -> buildDefaultGrid()
                is NavigatorAction.ProcessCommands -> calculateRoverPosition(action.config)
                is NavigatorAction.UpdateRoverPosition -> updateRoverPosition()
            }
        }

        override fun mapIntentToAction(intent: NavigatorIntent): NavigatorAction {
            return when (intent) {
                is NavigatorIntent.BuildDefault -> NavigatorAction.BuildDefault
                is NavigatorIntent.ProcessCommands -> NavigatorAction.ProcessCommands(intent.coordinatesData)
                is NavigatorIntent.UpdateRoverPosition -> NavigatorAction.UpdateRoverPosition
            }
        }

        private fun buildDefaultGrid() {
            setState(NavigatorState.Initial)
        }

        private fun calculateRoverPosition(config: NavigatorConfig) {
            // UseCase calculating.....
            setState(
                NavigatorState.ProcessCommands(
                    NavigatorUiState(
                        x = 3,
                        y = 4,
                        direction = NavigatorDirection.W.symbol,
                    ),
                ),
            )
        }

        private fun updateRoverPosition() {
            setState(
                NavigatorState.UpdateRoverPosition(
                    NavigatorUiState(
                        x = 3,
                        y = 4,
                        direction = NavigatorDirection.W.symbol,
                    ),
                ),
            )
        }
    }
