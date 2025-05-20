package com.rpla.marsrovernavigator.gridnavigator.viewmodel

import com.rpla.marsrovernavigator.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoverNavigatorViewModel
    @Inject
    constructor() :
    BaseViewModel<GridNavigatorIntent, GridNavigatorAction, GridNavigatorState>() {
        override fun createInitialState(): GridNavigatorState = GridNavigatorState.Initial

        override fun handleAction(action: GridNavigatorAction) {
            when (action) {
                GridNavigatorAction.BuildDefaultGrid -> buildDefaultGrid()
                is GridNavigatorAction.UpdateRoverPosition -> calculateRoverPosition()
            }
        }

        override fun mapIntentToAction(intent: GridNavigatorIntent): GridNavigatorAction {
            return when (intent) {
                is GridNavigatorIntent.BuildDefaultGrid -> GridNavigatorAction.BuildDefaultGrid
                is GridNavigatorIntent.ProcessCommands -> GridNavigatorAction.UpdateRoverPosition
            }
        }

        private fun buildDefaultGrid() {
            setState(GridNavigatorState.Initial)
        }

        private fun calculateRoverPosition() {
            TODO() // Usecase
        }
    }
