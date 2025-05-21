package com.rpla.marsrovernavigator.navigator.viewmodel

import androidx.lifecycle.viewModelScope
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.domain.usecase.GetNavigatorCoordinatesUseCase
import com.rpla.marsrovernavigator.navigator.ui.presentation.NavigatorUiState
import com.rpla.marsrovernavigator.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class NavigatorViewModel
    @Inject
    constructor(
        private val getNavigatorCoordinatesUseCase: GetNavigatorCoordinatesUseCase,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) :
    BaseViewModel<NavigatorIntent, NavigatorAction, NavigatorState>() {
        override fun createInitialState(): NavigatorState = NavigatorState.Initial

        override fun handleAction(action: NavigatorAction) {
            when (action) {
                is NavigatorAction.BuildDefault -> buildDefaultGrid()
                is NavigatorAction.ProcessCommands -> calculateRoverPosition(action.config)
                is NavigatorAction.UpdateRoverPosition -> updateRoverPosition(action.uiState)
            }
        }

        override fun mapIntentToAction(intent: NavigatorIntent): NavigatorAction {
            return when (intent) {
                is NavigatorIntent.BuildDefault -> NavigatorAction.BuildDefault
                is NavigatorIntent.ProcessCommands -> NavigatorAction.ProcessCommands(intent.coordinatesData)
                is NavigatorIntent.UpdateRoverPosition -> NavigatorAction.UpdateRoverPosition(intent.uiState)
            }
        }

        private fun buildDefaultGrid() {
            setState(NavigatorState.Initial)
        }

        private fun calculateRoverPosition(config: NavigatorConfig) {
            getNavigatorCoordinatesUseCase.invoke(
                viewModelScope,
                dispatcher,
                GetNavigatorCoordinatesUseCase.RequestValue(currentCoordinates = config),
            ) { processedCoordinates ->
                val uiState = parseCoordinatesToUiState(processedCoordinates?.rawCoordinates ?: "1 3 N")
                setState(
                    NavigatorState.ProcessCommands(
                        uiState,
                    ),
                )
            }
        }

        private fun updateRoverPosition(uiState: NavigatorUiState) {
            setState(
                NavigatorState.UpdateRoverPosition(
                    uiState,
                ),
            )
        }

        private fun parseCoordinatesToUiState(rawCoordinates: String): NavigatorUiState {
            val parts = rawCoordinates.split(" ")
            val rawX = parts[0].toInt()
            val rawY = parts[1].toInt()
            val rawDirection = parts[2]

            return NavigatorUiState(
                x = rawX,
                y = rawY,
                direction = rawDirection,
            )
        }
    }
